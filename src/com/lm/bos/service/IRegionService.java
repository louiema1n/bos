package com.lm.bos.service;

import java.util.List;

import com.lm.bos.domain.BcRegion;
import com.lm.bos.utils.PageBean;

public interface IRegionService {

	/**
	 * ��������
	 * @param list
	 */
	void saveOrUpdate(List<BcRegion> list);

	/**
	 * ��ҳ��ѯ
	 * @param pageBean
	 */
	void queryPage(PageBean pageBean);

	/**
	 * ��ѯ����
	 * @return
	 */
	List<BcRegion> findAll();

}
