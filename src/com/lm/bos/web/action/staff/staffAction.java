package com.lm.bos.web.action.staff;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.BcStaff;
import com.lm.bos.service.IStaffService;
import com.lm.bos.utils.PageBean;
import com.lm.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class staffAction extends BaseAction<BcStaff> {

	@Autowired
	private IStaffService staffService;
	
	//jsp传过来的分页查询数据,属性驱动自动封装数据
	private int page;	//当前页
	private int rows;	//每页显示记录数
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * 增加staff
	 */
	public String addStaff() {
		staffService.save(this.getModel());
		return "list";
	}
	
	/**
	 * 分页查询
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		//封装pagebean
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		//指定detachedCriteria的对象
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BcStaff.class);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		//查询
		staffService.queryPage(pageBean);
		
		//用json进行数据封装
		//过滤掉不需要封装的数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"currentPage","detachedCriteria","pageSize"});
		
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
		String data = jsonObject.toString();
		
		//将json格式的data返回页面
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(data);
		
		return NONE;
	}
}
