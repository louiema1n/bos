package com.lm.bos.service;

import com.lm.bos.domain.BcDecidedzone;
import com.lm.bos.utils.PageBean;

public interface IDecidedzoneService {

	/**
	 * 增加定区
	 * @param model
	 * @param sid
	 */
	void save(BcDecidedzone model, String[] sid);

	/**
	 * 分页查询
	 * @param pageBean
	 */
	void queryPage(PageBean pageBean);
	
}
