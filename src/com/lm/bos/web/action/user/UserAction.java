package com.lm.bos.web.action.user;

import java.io.IOException;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.User;
import com.lm.bos.service.IUserService;
import com.lm.bos.utils.MD5Utils;
import com.lm.bos.web.action.base.BaseAction;

@Controller//("abc")
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	
	//ͨ������������ȡjsp�ύ����֤��
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	/**
	 * ��¼
	 * @return
	 */
	public String login_bak() {
		//��ȡsession���е���֤��
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//�ж���֤��
		if (StringUtils.isNotBlank(checkcode) && validateCode.equals(checkcode)) {
			//��֤����ȷ,�����û���������֤
			User user = userService.login(this.getModel());
			if (user != null) {
				//��½�ɹ�,��user����session����,��ת��index.jsp
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return "index";
			}else {
				//��¼ʧ��,��ת��login.jsp,�����Դ�����Ϣ
				this.addActionError(this.getText("loginError"));
				return "login";
			}
		} else {
			//��֤�����,��ת��login.jsp,�����Դ�����Ϣ
			this.addActionError(this.getText("chkCodeError"));
			return "login";
		}
		
	}
	/**
	 * ��¼
	 * @return
	 */
	public String login() {
		//��ȡsession���е���֤��
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//�ж���֤��
		if (StringUtils.isNotBlank(checkcode) && validateCode.equals(checkcode)) {
			//��ȡ��ǰ�û�����
			Subject subject = SecurityUtils.getSubject();	//δ��֤
			//��ȡ��ǰ�ύ������
			String password = this.getModel().getPassword();
			//����һ���û�����������
			AuthenticationToken token = new UsernamePasswordToken(this.getModel().getUsername(), MD5Utils.md5(password));
			try {
				subject.login(token);
			} catch (UnknownAccountException e) {
				e.printStackTrace();
				this.addActionError(this.getText("notFoundAccount"));
				return "login";
			} catch (Exception e) {
				e.printStackTrace();
				this.addActionError(this.getText("errorPwd"));
				return "login";
			}
			//��֤ͨ��,��ȡ����SimpleAuthenticationInfo�����е�user
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return "index";
		} else {
			//��֤�����,��ת��login.jsp,�����Դ�����Ϣ
			this.addActionError(this.getText("chkCodeError"));
			return "login";
		}
		
	}
	
	/**
	 * �˳���½
	 */
	public String logout() {
		//����session,�����ص�¼����
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	
	/**
	 * �޸ĵ�ǰ��¼�û�������
	 * @throws IOException 
	 */
	public String editPassword() throws IOException {
		//�õ���ǰ��¼�û�
		User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		//����flag�����������֪���޸ĳɹ����
		String flag = "1";
		try {
			//ͨ����ǰ�û���id���ύ�޸ĵ���������и����������
			userService.updateUserById(this.getModel().getPassword(), loginUser.getId());
		} catch (Exception e) {
			// �޸�ʧ��
			flag = "0";
		}
		//��flag���������,���÷���ֵ����
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	/**
	 * ��ҳ��ѯ
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		userService.queryPage(pageBean);
		this.writePageBean2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","authRoles"});
		return NONE;
	}
	
	/**
	 * ����û�
	 */
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String add() {
		userService.add(this.getModel(), roleIds);
		return "list";
	}
}
