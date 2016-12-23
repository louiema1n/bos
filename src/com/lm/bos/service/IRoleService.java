package com.lm.bos.service;

import java.util.List;

import com.lm.bos.domain.AuthRole;
import com.lm.bos.utils.PageBean;

public interface IRoleService {

	void add(AuthRole model, String ids);

	void queryPage(PageBean pageBean);

	List<AuthRole> findAll();
	
}
