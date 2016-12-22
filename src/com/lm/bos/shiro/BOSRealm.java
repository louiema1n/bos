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
	 * 授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//根据当前登录用户,查询其对应的权限,授权
		User user = (User) principals.getPrimaryPrincipal();
		List<AuthFunction> list = null;
		if (user.getUsername().equals("admin")) {
			//超级管理员,查询所有权限,为用户授权
			list = functionDao.findAll();
		}else {
			//普通用户,根据id查询对象的权限
			list = functionDao.findListById(user.getId());
		}
		//授权
		for (AuthFunction authFunction : list) {
			info.addStringPermission(authFunction.getCode());
		}
		return info;
	}

	/**
	 * 认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		//通过密码查询用户
		User user = userService.findByUsername(usernamePasswordToken.getUsername());
		if (user != null) {
			//用户名存在,创建简单认证对象
			/**
			 * 参数一principal(主要的):签名,程序可以任意位置获取放入的对象
			 * 参数二credentials(凭证)从数据库中查询到的密码
			 * 参数三realmName当前realm名称
			 */
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getSimpleName());
			return info;
		} else {
			return null;
		}
	}

}
