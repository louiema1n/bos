package com.lm.bos.utils;

import org.apache.struts2.ServletActionContext;

import com.lm.bos.domain.User;

/**
 * ��ȡ�����Ĺ�����
 * @author xtztx
 *
 */
public class BOSContext {
	//��ȡ��ǰ��¼�û�
	public static User getLoginUser() {
		return (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
	}
}
