package com.lm.bos.service;

import com.lm.bos.domain.BcDecidedzone;
import com.lm.bos.utils.PageBean;

public interface IDecidedzoneService {

	/**
	 * ���Ӷ���
	 * @param model
	 * @param sid
	 */
	void save(BcDecidedzone model, String[] sid);

	/**
	 * ��ҳ��ѯ
	 * @param pageBean
	 */
	void queryPage(PageBean pageBean);
	
}
