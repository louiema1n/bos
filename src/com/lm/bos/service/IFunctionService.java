package com.lm.bos.service;

import java.util.List;

import com.lm.bos.domain.AuthFunction;
import com.lm.bos.utils.PageBean;

public interface IFunctionService {

	void queryPage(PageBean pageBean);

	List<AuthFunction> findAll();

	void add(AuthFunction model);


}
