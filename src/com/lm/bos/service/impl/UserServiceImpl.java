package com.lm.bos.service.impl;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.user.IUserDao;
import com.lm.bos.domain.AuthRole;
import com.lm.bos.domain.User;
import com.lm.bos.service.IUserService;
import com.lm.bos.utils.MD5Utils;
import com.lm.bos.utils.PageBean;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	//注入userDao
	@Autowired
	private IUserDao userDao;

	//通过user查找user
	@Override
	public User login(User user) {
		return userDao.findUserByUser(user.getUsername(), MD5Utils.md5(user.getPassword()));
	}

	@Override
	public void updateUserById(String password, String id) {
		//queryName来自于User.hbm.xml
		userDao.executeUpdate("updateUserById", MD5Utils.md5(password), id);
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
		
	}

	@Override
	public void queryPage(PageBean pageBean) {
		userDao.queryPage(pageBean);
		
	}

	@Override
	public void add(User model, String[] roleIds) {
		//密码加密
		model.setPassword(MD5Utils.md5(model.getPassword()));
		userDao.save(model); 	//持久化对象
		for (String roleId : roleIds) {
			//由user来维护role的外键关系
			AuthRole role = new AuthRole(roleId);
			model.getAuthRoles().add(role);
		}
		
	}

}
