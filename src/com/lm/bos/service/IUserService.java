package com.lm.bos.service;

import com.lm.bos.domain.User;
import com.lm.bos.utils.PageBean;

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

	public void queryPage(PageBean pageBean);

	public void add(User model, String[] roles);

}
