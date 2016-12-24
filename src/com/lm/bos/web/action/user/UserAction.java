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
	
	//通过属性驱动获取jsp提交的验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	/**
	 * 登录
	 * @return
	 */
	public String login_bak() {
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
	/**
	 * 登录
	 * @return
	 */
	public String login() {
		//获取session域中的验证码
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//判断验证码
		if (StringUtils.isNotBlank(checkcode) && validateCode.equals(checkcode)) {
			//获取当前用户对象
			Subject subject = SecurityUtils.getSubject();	//未认证
			//获取当前提交的密码
			String password = this.getModel().getPassword();
			//构造一个用户名密码令牌
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
			//验证通过,获取放入SimpleAuthenticationInfo对象中的user
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return "index";
		} else {
			//验证码错误,跳转至login.jsp,并回显错误信息
			this.addActionError(this.getText("chkCodeError"));
			return "login";
		}
		
	}
	
	/**
	 * 退出登陆
	 */
	public String logout() {
		//销毁session,并返回登录界面
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	
	/**
	 * 修改当前登录用户的密码
	 * @throws IOException 
	 */
	public String editPassword() throws IOException {
		//得到当前登录用户
		User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		//定义flag返回浏览器告知其修改成功与否
		String flag = "1";
		try {
			//通过当前用户的id和提交修改的新密码进行更新密码操作
			userService.updateUserById(this.getModel().getPassword(), loginUser.getId());
		} catch (Exception e) {
			// 修改失败
			flag = "0";
		}
		//将flag返回浏览器,设置返回值类型
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	/**
	 * 分页查询
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		userService.queryPage(pageBean);
		this.writePageBean2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","authRoles"});
		return NONE;
	}
	
	/**
	 * 添加用户
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
