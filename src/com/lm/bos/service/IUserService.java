package com.lm.bos.service;

import com.lm.bos.domain.User;

public interface IUserService {

	/**
	 * ��¼
	 * @param model
	 * @return
	 */
	public User login(User model);

	/**
	 * ����id����������û���Ϣ
	 * @param password
	 * @param id
	 */
	public void updateUserById(String password, String id);

	public User findByUsername(String username);

}
