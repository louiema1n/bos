package com.lm.bos.service;

import com.lm.bos.domain.User;

public interface IUserService {

	/**
	 * 登录
	 * @param model
	 * @return
	 */
	public User login(User model);

	/**
	 * 根据id和密码更新用户信息
	 * @param password
	 * @param id
	 */
	public void updateUserById(String password, String id);

	public User findByUsername(String username);

}
