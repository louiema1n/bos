package com.lm.bos.service;

import java.util.List;

import com.lm.bos.domain.BcRegion;
import com.lm.bos.utils.PageBean;

public interface IRegionService {

	/**
	 * 批量导入
	 * @param list
	 */
	void saveOrUpdate(List<BcRegion> list);

	/**
	 * 分页查询
	 * @param pageBean
	 */
	void queryPage(PageBean pageBean);

	/**
	 * 查询所有
	 * @return
	 */
	List<BcRegion> findAll();

}
