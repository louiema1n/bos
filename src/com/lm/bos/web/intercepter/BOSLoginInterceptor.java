package com.lm.bos.web.intercepter;

import org.apache.struts2.ServletActionContext;

import com.lm.bos.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 自定义登录验证拦截器(方法过滤拦截器)
 * @author xtztx
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//获取session中登录的用户
		User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		//判断用户是否存在
		if (loginUser == null) {
			//用户未登录
			return "login";
		}
		//用户已登录,放行
		return invocation.invoke();
	}

}
