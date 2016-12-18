package com.lm.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.region.IRegionDao;
import com.lm.bos.domain.BcRegion;
import com.lm.bos.service.IRegionService;
import com.lm.bos.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService {

	@Resource
	private IRegionDao regionDao;

	@Override
	public void saveOrUpdate(List<BcRegion> list) {
		for (BcRegion bcRegion : list) {
			regionDao.saveOrUpdate(bcRegion);
		}
		
	}

	@Override
	public void queryPage(PageBean pageBean) {
		regionDao.queryPage(pageBean);
		
	}

	@Override
	public List<BcRegion> findAll() {
		return regionDao.findAll();
	}

}
