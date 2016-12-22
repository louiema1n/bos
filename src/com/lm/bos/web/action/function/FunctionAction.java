package com.lm.bos.web.action.function;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.AuthFunction;
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
}
