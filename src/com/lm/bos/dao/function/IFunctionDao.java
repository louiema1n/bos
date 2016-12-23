package com.lm.bos.dao.function;

import java.util.List;

import com.lm.bos.dao.base.IBaseDao;
import com.lm.bos.domain.AuthFunction;

public interface IFunctionDao extends IBaseDao<AuthFunction> {

	List<AuthFunction> findListById(String id);

	List<AuthFunction> findAllMenu();

	List<AuthFunction> findMenuById(String id);


}
