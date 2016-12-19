package com.lm.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.decidedzone.IDecidedzoneDao;
import com.lm.bos.dao.subarea.ISubareaDao;
import com.lm.bos.domain.BcDecidedzone;
import com.lm.bos.domain.BcSubarea;
import com.lm.bos.service.IDecidedzoneService;
import com.lm.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {
	
	@Resource
	private IDecidedzoneDao decidedzoneDao;
	
	@Resource
	private ISubareaDao subareaDao;

	@Override
	public void save(BcDecidedzone model, String[] sid) {
		decidedzoneDao.save(model);
		//定区对分区是一对多,需要在.hbm.xml文件中声明外键关系维护的放弃
		//一的一方放弃了外键关系的维护
		for (String id : sid) {
			//遍历查询出subarea-->持久化对象
			BcSubarea subarea = subareaDao.findById(id);
			//由多的一方来维护外键关系
			subarea.setBcDecidedzone(model); 	//缓存,自动发送sql
		}
		
	}

	@Override
	public void queryPage(PageBean pageBean) {
		decidedzoneDao.queryPage(pageBean);
		
	}

}
