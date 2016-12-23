package com.lm.bos.utils;

import org.apache.struts2.ServletActionContext;

import com.lm.bos.domain.User;

/**
 * 获取上下文工具类
 * @author xtztx
 *
 */
public class BOSContext {
	//获取当前登录用户
	public static User getLoginUser() {
		return (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
	}
}
