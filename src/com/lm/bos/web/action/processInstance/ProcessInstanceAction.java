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
 * ����ʵ��action
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
	 * ��ѯ��������ʵ��
	 * @return
	 */
	public String list() {
		//��������ʵ����ѯ����
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
		//������ʵ��id����
		query.orderByProcessInstanceId().desc();
		//ִ�в�ѯ
		List<ProcessInstance> list = query.list();
		//����ֵջ
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	/**
	 * ��ѯҵ������
	 */
	//��������ʵ��id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String findData() throws IOException {
		//ͨ������ʵ��id��ѯ�����̱���
		Map<String, Object> variables = runtimeService.getVariables(id);
		//д�ص�jsp
		ServletActionContext.getResponse().setContentType("html/text;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(variables.toString());
		return NONE;
	}
	
	//action�еĳ�Ա����Ĭ�Ϸ���ֵջ,�ṩget������jsp��ȡ,����Ҫ�ٴ�����ֵջ
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
	 * ��������ʵ��id��ѯ����id,ͼƬ���ƺ�����
	 */
	public String showPng() {
		//��������ʵ��id��ѯ����ʵ������
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().singleResult();
		//����ʵ�������ѯ���̶���id
		String processDefinitionId = processInstance.getProcessDefinitionId();
		//�������̶���id�������̶������
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		//��ѯ���̲���id��ͼƬ����
		deploymentId = processDefinition.getDeploymentId();
		imageName = processDefinition.getDiagramResourceName();
		
		//��ѯ����
		//��ȡ����ʵ�����е��ĸ��ڵ�
		String activityId = processInstance.getActivityId();
		//����bpmn�ļ�,���һ�����̶������,��ѯ�����Ʊ�
		ProcessDefinitionEntity processDefinition2 = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//��ð�������Ķ���
		ActivityImpl findActivity = processDefinition2.findActivity(activityId);
		
		int x = findActivity.getX();
		int y = findActivity.getY();
		int width = findActivity.getWidth();
		int height = findActivity.getHeight();
		
		//����ֵջ
		ActionContext.getContext().getValueStack().set("x", x);
		ActionContext.getContext().getValueStack().set("y", y);
		ActionContext.getContext().getValueStack().set("width", width);
		ActionContext.getContext().getValueStack().set("height", height);
		
		return "showPng";
	}
	
	/**
	 * ��ʾͼƬ
	 */
	public String viewImage() {
		//�������̲���id��ͼƬ���ƻ�ȡͼƬ������
		InputStream imgStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("imgStream", imgStream);
		return "viewImage";
	}
}
