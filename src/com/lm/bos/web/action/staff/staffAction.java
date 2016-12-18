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
	
	//jsp�������ķ�ҳ��ѯ����,���������Զ���װ����
	private int page;	//��ǰҳ
	private int rows;	//ÿҳ��ʾ��¼��
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

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
		//��װpagebean
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		//ָ��detachedCriteria�Ķ���
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BcStaff.class);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		//��ѯ
		staffService.queryPage(pageBean);
		
		//��json�������ݷ�װ
		//���˵�����Ҫ��װ������
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"currentPage","detachedCriteria","pageSize"});
		
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
		String data = jsonObject.toString();
		
		//��json��ʽ��data����ҳ��
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(data);
		
		return NONE;
	}
}
