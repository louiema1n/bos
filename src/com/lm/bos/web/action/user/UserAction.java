package com.lm.bos.web.action.user;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.User;
import com.lm.bos.service.IUserService;
import com.lm.bos.web.action.base.BaseAction;

@Controller//("abc")
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	
	//ע��userService
	@Resource
	private IUserService userService;
	
	//ͨ������������ȡjsp�ύ����֤��
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	//��¼
	public String login() {
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
}
