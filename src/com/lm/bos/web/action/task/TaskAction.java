package com.lm.bos.web.action.task;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.utils.BOSContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport {
	
	@Autowired
	private TaskService taskService;
	/**
	 * ��ѯ������
	 * @return
	 */
	public String findGroupTask() {
		TaskQuery query = taskService.createTaskQuery();
		String candidateUser = BOSContext.getLoginUser().getId();	//��ǰ��¼�û�id
		List<Task> list = query.taskCandidateUser(candidateUser).list();		//���������
		//ѹջ
		ActionContext.getContext().getValueStack().set("list", list);
		return "findGroupTask";
	}
	
	/**
	 * ��ѯ���̱���
	 */
	private String taskId;
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String showData() throws IOException {
		Map<String, Object> variables = taskService.getVariables(taskId);
		//д��ҳ��
		ServletActionContext.getResponse().setContentType("html/text;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(variables.toString());
		return NONE;
	}
	
	/**
	 * ʰȡ����
	 */
	public String takeTask() {
		taskService.claim(taskId, BOSContext.getLoginUser().getId());
		return "tofindGroup";
	}
	
	/**
	 * ��ѯ��������
	 * @return
	 */
	public String findPersonalTask() {
		TaskQuery query = taskService.createTaskQuery();
		String candidateUser = BOSContext.getLoginUser().getId();	//��ǰ��¼�û�id
		List<Task> list = query.taskAssignee(candidateUser).list();		//���������
		//ѹջ
		ActionContext.getContext().getValueStack().set("list", list);
		return "findPersonalTask";
	}
}
