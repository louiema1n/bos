package com.lm.bos.web.action.workordermanage;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.QpWorkordermanage;
import com.lm.bos.service.IWorkordermanageService;
import com.lm.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<QpWorkordermanage> {
	
	/**
	 * ����������
	 * @throws IOException 
	 */
	public String add() throws IOException {
		String flag = "1";	//1-�ɹ�;0-ʧ��
		try {
			workordermanageService.add(this.getModel());
		} catch (Exception e) {
			e.printStackTrace();
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("html/text;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	/**
	 * ��ҳ��ѯ
	 * @throws IOException 
	 */
	public String querypage() throws IOException {
		workordermanageService.querypage(pageBean);
		this.writePageBean2Json(pageBean, new String[]{});
		return NONE;
	}
	
	/**
	 * ��ѯstartΪ0�Ĺ�����
	 * @throws IOException 
	 */
	public String list() throws IOException {
		List<QpWorkordermanage> list = workordermanageService.listNoStart();
		//ѹ��ֵջ
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	/**
	 * ��������ʵ��
	 */
	public String start() {
		workordermanageService.start(this.getModel().getId());
		return "tolist";
	}
}
