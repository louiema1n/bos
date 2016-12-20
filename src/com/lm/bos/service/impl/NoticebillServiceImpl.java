package com.lm.bos.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.crm.CustomerService;
import com.lm.bos.dao.decidedzone.IDecidedzoneDao;
import com.lm.bos.dao.noticebill.INoticebillDao;
import com.lm.bos.dao.workbill.IWorkbillDao;
import com.lm.bos.domain.BcDecidedzone;
import com.lm.bos.domain.QpNoticebill;
import com.lm.bos.domain.QpWorkbill;
import com.lm.bos.service.INoticebillService;

@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService {

	@Resource
	private INoticebillDao noticebillDao;
	
	@Resource
	private CustomerService proxy;
	
	@Resource
	private IDecidedzoneDao decidedzoneDao;
	
	@Resource
	private IWorkbillDao workbillDao;
	
	@Override
	public void save(QpNoticebill model) {
		noticebillDao.save(model);
		//根据取件地址pickaddress调用crm查询相应定区id,尝试自动分单
		String pickaddress = model.getPickaddress();
		String decidedzoneId = proxy.findDecidedzoneIdByAddr(pickaddress);
		if (decidedzoneId != null) {
			//可以自动分单
			//根据decidedzoneId查询到关联的取派员staff
			BcDecidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			String staffid = decidedzone.getBcStaff().getId();
			//业务通知单关联查询到的取派员id
			model.setStaffId(staffid);
			//设置为自动分单
			model.setOrdertype("自动");
			
			//为取派员staff创建一个工单workbill
			QpWorkbill qpWorkbill = new QpWorkbill();
			qpWorkbill.setPickstate("未取件");
			qpWorkbill.setBuildtime(new Date());
			qpWorkbill.setQpNoticebill(model);
			qpWorkbill.setRemark(model.getRemark());
			qpWorkbill.setStaffId(staffid);
			qpWorkbill.setType("新单");
			
			workbillDao.save(qpWorkbill);
			//调用短信平台,发送短信
		} else {
			//只能人工分单
			model.setOrdertype("人工");
		}
		
	}

}
