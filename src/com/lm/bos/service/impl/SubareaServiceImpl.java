package com.lm.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.subarea.ISubareaDao;
import com.lm.bos.domain.BcSubarea;
import com.lm.bos.service.ISubareaService;
import com.lm.bos.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	
	@Resource
	private ISubareaDao subareaDao;

	@Override
	public void add(BcSubarea model) {
		subareaDao.save(model);
		
	}

	@Override
	public void queryPage(PageBean pageBean) {
		subareaDao.queryPage(pageBean);
		
	}

	@Override
	public List<BcSubarea> findAll() {
		return subareaDao.findAll();
	}

	@Override
	public List<BcSubarea> queryNoBcDecidedzone() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BcSubarea.class);
		detachedCriteria.add(Restrictions.isNull("bcDecidedzone"));	//isNull对象为空;isEmpty集合为空
		return subareaDao.findByCriteria(detachedCriteria);
	}
}
