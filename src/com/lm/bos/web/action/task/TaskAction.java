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
}
