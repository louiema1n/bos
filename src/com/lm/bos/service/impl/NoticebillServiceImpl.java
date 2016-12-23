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
		//����ȡ����ַpickaddress����crm��ѯ��Ӧ����id,�����Զ��ֵ�
		String pickaddress = model.getPickaddress();
		String decidedzoneId = proxy.findDecidedzoneIdByAddr(pickaddress);
		if (decidedzoneId != null) {
			//�����Զ��ֵ�
			//����decidedzoneId��ѯ��������ȡ��Աstaff
			BcDecidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			String staffid = decidedzone.getBcStaff().getId();
			//ҵ��֪ͨ��������ѯ����ȡ��Աid
			model.setStaffId(staffid);
			//����Ϊ�Զ��ֵ�
			model.setOrdertype("�Զ�");
			
			//Ϊȡ��Աstaff����һ������workbill
			QpWorkbill qpWorkbill = new QpWorkbill();
			qpWorkbill.setPickstate("δȡ��");
			qpWorkbill.setBuildtime(new Date());
			qpWorkbill.setQpNoticebill(model);
			qpWorkbill.setRemark(model.getRemark());
			qpWorkbill.setStaffId(staffid);
			qpWorkbill.setType("�µ�");
			
			workbillDao.save(qpWorkbill);
			//���ö���ƽ̨,���Ͷ���
		} else {
			//ֻ���˹��ֵ�
			model.setOrdertype("�˹�");
		}
		
	}

}
