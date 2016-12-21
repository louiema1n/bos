package com.lm.bos.dao.user.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.lm.bos.dao.base.impl.BaseDaoImpl;
import com.lm.bos.dao.user.IUserDao;
import com.lm.bos.domain.User;

@Repository
//@Scope("prototype")		//多例
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	@Override
	public User findUserByUser(String username, String password) {
		String hql = "FROM User u where u.username = ? AND u.password = ? ";
		List users = this.getHibernateTemplate().find(hql, username, password);
		if (users != null && users.size() != 0) {
			//查询到user
			return (User) users.get(0);
		}
		return null;
	}

	@Override
	public User findByUsername(String username) {
		String hql = "FROM User u where u.username = ? ";
		List users = this.getHibernateTemplate().find(hql, username);
		if (users != null && users.size() != 0) {
			//查询到user
			return (User) users.get(0);
		}
		return null;
	}

}
