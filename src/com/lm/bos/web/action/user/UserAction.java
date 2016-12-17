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
	
	//注入userService
	@Resource
	private IUserService userService;
	
	//通过属性驱动获取jsp提交的验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	//登录
	public String login() {
		//获取session域中的验证码
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//判断验证码
		if (StringUtils.isNotBlank(checkcode) && validateCode.equals(checkcode)) {
			//验证码正确,进行用户名密码验证
			User user = userService.login(this.getModel());
			if (user != null) {
				//登陆成功,将user放入session域中,跳转至index.jsp
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return "index";
			}else {
				//登录失败,跳转至login.jsp,并回显错误信息
				this.addActionError(this.getText("loginError"));
				return "login";
			}
		} else {
			//验证码错误,跳转至login.jsp,并回显错误信息
			this.addActionError(this.getText("chkCodeError"));
			return "login";
		}
		
	}
}
