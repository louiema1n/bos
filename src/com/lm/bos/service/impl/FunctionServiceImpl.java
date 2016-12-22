package com.lm.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.function.IFunctionDao;
import com.lm.bos.domain.AuthFunction;
import com.lm.bos.service.IFunctionService;
import com.lm.bos.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	@Resource
	private IFunctionDao functionDao;

	@Override
	public void queryPage(PageBean pageBean) {
		functionDao.queryPage(pageBean);
		
	}

	@Override
	public List<AuthFunction> findAll() {
		List<AuthFunction> list = functionDao.findAll();
		return list;
	}

	@Override
	public void add(AuthFunction model) {
		functionDao.save(model);
	}


}
