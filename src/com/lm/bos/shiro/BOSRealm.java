package com.lm.bos.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.lm.bos.dao.function.IFunctionDao;
import com.lm.bos.domain.AuthFunction;
import com.lm.bos.domain.User;
import com.lm.bos.service.IUserService;

public class BOSRealm extends AuthorizingRealm {
	@Resource
	private IUserService userService;
	
	@Resource
	private IFunctionDao functionDao;

	/**
	 * ��Ȩ
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//���ݵ�ǰ��¼�û�,��ѯ���Ӧ��Ȩ��,��Ȩ
		User user = (User) principals.getPrimaryPrincipal();
		List<AuthFunction> list = null;
		if (user.getUsername().equals("admin")) {
			//��������Ա,��ѯ����Ȩ��,Ϊ�û���Ȩ
			list = functionDao.findAll();
		}else {
			//��ͨ�û�,����id��ѯ�����Ȩ��
			list = functionDao.findListById(user.getId());
		}
		//��Ȩ
		for (AuthFunction authFunction : list) {
			info.addStringPermission(authFunction.getCode());
		}
		return info;
	}

	/**
	 * ��֤
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		//ͨ�������ѯ�û�
		User user = userService.findByUsername(usernamePasswordToken.getUsername());
		if (user != null) {
			//�û�������,��������֤����
			/**
			 * ����һprincipal(��Ҫ��):ǩ��,�����������λ�û�ȡ����Ķ���
			 * ������credentials(ƾ֤)�����ݿ��в�ѯ��������
			 * ������realmName��ǰrealm����
			 */
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getSimpleName());
			return info;
		} else {
			return null;
		}
	}

}
