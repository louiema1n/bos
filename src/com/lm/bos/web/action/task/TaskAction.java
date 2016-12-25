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
	 * 查询组任务
	 * @return
	 */
	public String findGroupTask() {
		TaskQuery query = taskService.createTaskQuery();
		String candidateUser = BOSContext.getLoginUser().getId();	//当前登录用户id
		List<Task> list = query.taskCandidateUser(candidateUser).list();		//组任务过滤
		//压栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "findGroupTask";
	}
	
	/**
	 * 查询流程变量
	 */
	private String taskId;
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String showData() throws IOException {
		Map<String, Object> variables = taskService.getVariables(taskId);
		//写回页面
		ServletActionContext.getResponse().setContentType("html/text;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(variables.toString());
		return NONE;
	}
	
	/**
	 * 拾取任务
	 */
	public String takeTask() {
		taskService.claim(taskId, BOSContext.getLoginUser().getId());
		return "tofindGroup";
	}
	
	/**
	 * 查询个人任务
	 * @return
	 */
	public String findPersonalTask() {
		TaskQuery query = taskService.createTaskQuery();
		String candidateUser = BOSContext.getLoginUser().getId();	//当前登录用户id
		List<Task> list = query.taskAssignee(candidateUser).list();		//组任务过滤
		//压栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "findPersonalTask";
	}
	
	/**
	 * 办理任务
	 */
	private Integer check;	//审核结果:0-审核不通过;1-通过
	public Integer getCheck() {
		return check;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}

	public String checkWorkOrderManage() {
		//根据taskid获取业务数据
		//根据taskid获取任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//根据任务对象获取流程实例id
		String processInstanceId = task.getProcessInstanceId();
		//根据流程实例id获取流程实例对象
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		//根据流程实例对象获取业务主键--workordermanageid
		String workordermanageId = processInstance.getBusinessKey();
		//根据workordermanageId查询workordermanage
		QpWorkordermanage workordermanage = workordermanageDao.findById(workordermanageId);
		if (check == null) {
			//跳转到审核界面
			//放入值栈
			ActionContext.getContext().getValueStack().set("map", workordermanage);
			return "check";
		} else {
			//办理审核任务
			workordermanageService.checkWorkOrderManage(workordermanageId, processInstanceId, check, taskId);
			return "tofindPersonalTask";
		}
	}
}
