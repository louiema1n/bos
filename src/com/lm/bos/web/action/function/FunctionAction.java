package com.lm.bos.web.action.function;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.dao.function.IFunctionDao;
import com.lm.bos.domain.AuthFunction;
import com.lm.bos.domain.User;
import com.lm.bos.utils.BOSContext;
import com.lm.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<AuthFunction> {

	/**
	 * 查询分页
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		//authfunction中也包含page属性,struts会优先注入模型驱动
		pageBean.setCurrentPage(Integer.parseInt(this.getModel().getPage()));
		functionService.queryPage(pageBean);
		this.writePageBean2Json(pageBean, new String[]{"authFunctions","authFunction","authRoles","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	/**
	 * 查询所有function
	 * @throws IOException 
	 */
	public String listajax() throws IOException {
		List<AuthFunction> list = functionService.findAll();
		this.writeList2Json(list, new String[] {"authFunctions","authRoles","authFunction"});
		return NONE;
	}
	
	/**
	 * 增加function
	 */
	public String add() {
		//如果authFunction不为空，但pid为""，需将authFunction设为空
		if (this.getModel().getAuthFunction() != null && this.getModel().getAuthFunction().getId().equals("")) {
			this.getModel().setAuthFunction(null);
		}
		functionService.add(this.getModel());
		return "list";
	}
	
	/**
	 * 加载菜单
	 */
	@Resource
	private IFunctionDao functionDao;
	public String findMenu() throws IOException {
		//获取当前登录用户
		User loginUser = BOSContext.getLoginUser();
		List<AuthFunction> list = null;
		if (loginUser.getUsername().equals("admin")) {
			//超级管理员,查询所有菜单
			list = functionDao.findAllMenu();
		} else {
			//普通用户,查询id对应菜单
			list = functionDao.findMenuById(loginUser.getId());
		}
		
		this.writeList2Json(list, new String[]{"authFunction","authFunctions","authRoles"});
		return NONE;
	}
}
