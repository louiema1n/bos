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
	 * ����staff
	 */
	public String addStaff() {
		staffService.save(this.getModel());
		return "list";
	}
	
	/**
	 * ��ҳ��ѯ
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		
		//��ѯ
		staffService.queryPage(pageBean);
		//��װjson
		this.writePageBean2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","bcDecidedzones"});
		return NONE;
	}
	
	/**
	 * ��������staff
	 */
	//id
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	@RequiresPermissions(value="staff")	//ִ�д˷�����ҪstaffȨ��
	@RequiresRoles(value="abc")
	public String deleteBatch() {
		staffService.deleteBatch(ids);
		return "list";
	}
	
	/**
	 * �޸�����Ա��Ϣ
	 */
	public String editStaff() {
		//����staff�в��������ֶζ�����и���,������Ҫ�Ƚ����ݿ����Ѵ��ڵ����ݲ��
		BcStaff oldStaff = staffService.query(this.getModel());
		//���޸ĵ����ݸ���ԭ����
		oldStaff.setName(this.getModel().getName());
		oldStaff.setTelephone(this.getModel().getTelephone());
		oldStaff.setHaspda(this.getModel().getHaspda());
		oldStaff.setStandard(this.getModel().getStandard());
		oldStaff.setStation(this.getModel().getStation());
		
		//�ٸ������ݿ�
		staffService.update(oldStaff);
		
		return "list";
	}
	
	/**
	 * ��ѯû�����ϵ�����Ա����(deltag = '1')
	 * @throws IOException 
	 */
	public String listAjax() throws IOException {
		List<BcStaff> list = staffService.findByDeltag();
		//���˵��������ֶ�
		this.writeList2Json(list, new String[]{"bcDecidedzones"});
		return NONE;
	}
	
}
