package com.lm.bos.web.action.staff;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
		
		//查询
		staffService.queryPage(pageBean);
		//封装json
		this.writePageBean2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","bcDecidedzones"});
		return NONE;
	}
	
	/**
	 * 批量作废staff
	 */
	//id
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	@RequiresPermissions(value="staff")	//执行此方法需要staff权限
	@RequiresRoles(value="abc")
	public String deleteBatch() {
		staffService.deleteBatch(ids);
		return "list";
	}
	
	/**
	 * 修改收派员信息
	 */
	public String editStaff() {
		//由于staff中不是所有字段都会进行更新,所有需要先将数据库中已存在的数据查出
		BcStaff oldStaff = staffService.query(this.getModel());
		//将修改的数据覆盖原数据
		oldStaff.setName(this.getModel().getName());
		oldStaff.setTelephone(this.getModel().getTelephone());
		oldStaff.setHaspda(this.getModel().getHaspda());
		oldStaff.setStandard(this.getModel().getStandard());
		oldStaff.setStation(this.getModel().getStation());
		
		//再更新数据库
		staffService.update(oldStaff);
		
		return "list";
	}
	
	/**
	 * 查询没有作废的收派员数据(deltag = '1')
	 * @throws IOException 
	 */
	public String listAjax() throws IOException {
		List<BcStaff> list = staffService.findByDeltag();
		//过滤掉关联表字段
		this.writeList2Json(list, new String[]{"bcDecidedzones"});
		return NONE;
	}
	
}
