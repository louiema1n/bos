package com.lm.bos.web.action.role;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.AuthRole;
import com.lm.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<AuthRole> {
	/**
	 * 增加角色
	 */
	//接收页面传递过来的ids
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}

	public String add() {
		roleService.add(this.getModel(), ids);
		return "list";
	}
	
	/**
	 * 分页查询
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		roleService.queryPage(pageBean);
		this.writePageBean2Json(pageBean, new String[] {"currentPage","detachedCriteria","pageSize","authFunctions","users"});
		return NONE;
	}
	
	/**
	 * 查询所有
	 * @throws IOException 
	 */
	public String findAll() throws IOException {
		List<AuthRole> list = roleService.findAll();
		this.writeList2Json(list, new String[]{"currentPage","detachedCriteria","pageSize","authFunctions","users"});
		
		return NONE;
	}

}
