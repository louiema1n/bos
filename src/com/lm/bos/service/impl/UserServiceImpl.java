package com.lm.bos.service.impl;

import java.text.SimpleDateFormat;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.role.IRoleDao;
import com.lm.bos.dao.user.IUserDao;
import com.lm.bos.domain.AuthRole;
import com.lm.bos.domain.User;
import com.lm.bos.service.IUserService;
import com.lm.bos.utils.MD5Utils;
import com.lm.bos.utils.PageBean;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	//ע��userDao
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IdentityService identityService;

	//ͨ��user����user
	@Override
	public User login(User user) {
		return userDao.findUserByUser(user.getUsername(), MD5Utils.md5(user.getPassword()));
	}

	@Override
	public void updateUserById(String password, String id) {
		//queryName������User.hbm.xml
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

	/**
	 * �����û�,ͬ����activiti��act_id_user,act_id_membership
	 */
	@Override
	public void add(User model, String[] roleIds) {
		//�������
		model.setPassword(MD5Utils.md5(model.getPassword()));
		userDao.save(model); 	//�־û�����
		//���û�id��ΪactUser  id
		org.activiti.engine.identity.User actUser = new UserEntity(model.getId());
		identityService.saveUser(actUser );
		
		for (String roleId : roleIds) {
			//��user��ά��role�������ϵ
			AuthRole role = roleDao.findById(roleId);
			model.getAuthRoles().add(role);
			//�޸�act_id_membership
			identityService.createMembership(actUser.getId(), role.getName());
		}
		
	}

}
