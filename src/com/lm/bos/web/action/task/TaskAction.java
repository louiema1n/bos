package com.lm.bos.web.action.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.dao.workordermanage.IWorkordermanageDao;
import com.lm.bos.domain.QpWorkordermanage;
import com.lm.bos.service.IWorkordermanageService;
import com.lm.bos.utils.BOSContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IWorkordermanageDao workordermanageDao;
	
	@Autowired
	private IWorkordermanageService workordermanageService;
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
	
	/**
	 * ��������
	 */
	private Integer check;	//��˽��:0-��˲�ͨ��;1-ͨ��
	public Integer getCheck() {
		return check;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}

	public String checkWorkOrderManage() {
		//����taskid��ȡҵ������
		//����taskid��ȡ�������
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//������������ȡ����ʵ��id
		String processInstanceId = task.getProcessInstanceId();
		//��������ʵ��id��ȡ����ʵ������
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		//��������ʵ�������ȡҵ������--workordermanageid
		String workordermanageId = processInstance.getBusinessKey();
		//����workordermanageId��ѯworkordermanage
		QpWorkordermanage workordermanage = workordermanageDao.findById(workordermanageId);
		if (check == null) {
			//��ת����˽���
			//����ֵջ
			ActionContext.getContext().getValueStack().set("map", workordermanage);
			return "check";
		} else {
			//�����������
			workordermanageService.checkWorkOrderManage(workordermanageId, processInstanceId, check, taskId);
			return "tofindPersonalTask";
		}
	}
}
