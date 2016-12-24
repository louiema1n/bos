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
	 * 保存角色,同步到activiti组表中
	 */
	@Override
	public void add(AuthRole model, String ids) {
		roleDao.save(model); 	//持久对象
		
		//使用角色的名称作为租表的id
		Group group = new GroupEntity(model.getName());
		identityService.saveGroup(group);
		
		//hbm中显示是AuthFunction放弃了维护role_function的外键
		String[] strings = StringUtils.split(ids, ",");
		for (String fid : strings) {
			//角色维护权限
			AuthFunction function = new AuthFunction(fid);	//托管
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
