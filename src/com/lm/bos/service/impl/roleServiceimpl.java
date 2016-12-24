package com.lm.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.role.IRoleDao;
import com.lm.bos.domain.AuthFunction;
import com.lm.bos.domain.AuthRole;
import com.lm.bos.service.IRoleService;
import com.lm.bos.utils.PageBean;

@Service
@Transactional
public class roleServiceimpl implements IRoleService {
	
	@Resource
	private IRoleDao roleDao;
	
	@Resource
	private IdentityService identityService;

	/**
	 * �����ɫ,ͬ����activiti�����
	 */
	@Override
	public void add(AuthRole model, String ids) {
		roleDao.save(model); 	//�־ö���
		
		//ʹ�ý�ɫ��������Ϊ����id
		Group group = new GroupEntity(model.getName());
		identityService.saveGroup(group);
		
		//hbm����ʾ��AuthFunction������ά��role_function�����
		String[] strings = StringUtils.split(ids, ",");
		for (String fid : strings) {
			//��ɫά��Ȩ��
			AuthFunction function = new AuthFunction(fid);	//�й�
			model.getAuthFunctions().add(function);
		}
		
	}

	@Override
	public void queryPage(PageBean pageBean) {
		roleDao.queryPage(pageBean);
		
	}

	@Override
	public List<AuthRole> findAll() {
		return roleDao.findAll();
	}

}
