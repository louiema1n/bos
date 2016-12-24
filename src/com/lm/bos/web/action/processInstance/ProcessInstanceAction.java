package com.lm.bos.web.action.processInstance;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程实例action
 * @author xtztx
 *
 */
@Controller
@Scope("prototype")
public class ProcessInstanceAction extends ActionSupport {
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	/**
	 * 查询所有流程实例
	 * @return
	 */
	public String list() {
		//创建流程实例查询对象
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
		//按流程实例id排序
		query.orderByProcessInstanceId().desc();
		//执行查询
		List<ProcessInstance> list = query.list();
		//放入值栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	/**
	 * 查询业务数据
	 */
	//接收流程实例id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String findData() throws IOException {
		//通过流程实例id查询到流程变量
		Map<String, Object> variables = runtimeService.getVariables(id);
		//写回到jsp
		ServletActionContext.getResponse().setContentType("html/text;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(variables.toString());
		return NONE;
	}
	
	//action中的成员变量默认放在值栈,提供get方法供jsp获取,不需要再次亚茹值栈
	private String deploymentId;
	private String imageName;
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	/**
	 * 根据流程实例id查询部署id,图片名称和坐标
	 */
	public String showPng() {
		//根据流程实例id查询流程实例对象
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().singleResult();
		//根据实例对象查询流程定义id
		String processDefinitionId = processInstance.getProcessDefinitionId();
		//根据流程定义id创建流程定义对象
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		//查询流程部署id和图片名称
		deploymentId = processDefinition.getDeploymentId();
		imageName = processDefinition.getDiagramResourceName();
		
		//查询坐标
		//获取流程实例运行到哪个节点
		String activityId = processInstance.getActivityId();
		//加载bpmn文件,获得一个流程定义对象,查询二进制表
		ProcessDefinitionEntity processDefinition2 = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//获得包含坐标的对象
		ActivityImpl findActivity = processDefinition2.findActivity(activityId);
		
		int x = findActivity.getX();
		int y = findActivity.getY();
		int width = findActivity.getWidth();
		int height = findActivity.getHeight();
		
		//放入值栈
		ActionContext.getContext().getValueStack().set("x", x);
		ActionContext.getContext().getValueStack().set("y", y);
		ActionContext.getContext().getValueStack().set("width", width);
		ActionContext.getContext().getValueStack().set("height", height);
		
		return "showPng";
	}
	
	/**
	 * 显示图片
	 */
	public String viewImage() {
		//根据流程部署id和图片名称获取图片输入流
		InputStream imgStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("imgStream", imgStream);
		return "viewImage";
	}
}
