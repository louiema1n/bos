package com.lm.bos.service;

import com.lm.bos.domain.BcStaff;
import com.lm.bos.utils.PageBean;

public interface IStaffService {

	/**
	 * ����staff
	 */
	void save(BcStaff model);

	/**
	 * ��ҳ��ѯ
	 * @param pageBean
	 */
	void queryPage(PageBean pageBean);

	/**
	 * ��������staff
	 * @param ids
	 */
	void deleteBatch(String ids);

	/**
	 * ��ѯstaff
	 * @param model
	 * @return
	 */
	BcStaff query(BcStaff model);

	/**
	 * �޸�staff
	 * @param oldStaff
	 */
	void update(BcStaff oldStaff);

}
