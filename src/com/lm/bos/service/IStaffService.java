package com.lm.bos.service;

import com.lm.bos.domain.BcStaff;
import com.lm.bos.utils.PageBean;

public interface IStaffService {

	/**
	 * 增加staff
	 */
	void save(BcStaff model);

	/**
	 * 分页查询
	 * @param pageBean
	 */
	void queryPage(PageBean pageBean);

	/**
	 * 批量作废staff
	 * @param ids
	 */
	void deleteBatch(String ids);

	/**
	 * 查询staff
	 * @param model
	 * @return
	 */
	BcStaff query(BcStaff model);

	/**
	 * 修改staff
	 * @param oldStaff
	 */
	void update(BcStaff oldStaff);

}
