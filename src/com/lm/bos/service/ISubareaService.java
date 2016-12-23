package com.lm.bos.service;

import java.util.List;

import com.lm.bos.domain.BcSubarea;
import com.lm.bos.utils.PageBean;

public interface ISubareaService {

	void add(BcSubarea model);

	void queryPage(PageBean pageBean);

	List<BcSubarea> findAll();

	List<BcSubarea> queryNoBcDecidedzone();
	
}
