package com.lm.bos.dao.user;

import com.lm.bos.dao.base.IBaseDao;
import com.lm.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	/**
	 * ͨ��username��password����user
	 * @param user
	 * @return
	 */
	User findUserByUser(String username, String password);

}
