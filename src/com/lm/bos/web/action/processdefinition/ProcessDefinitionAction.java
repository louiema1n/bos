package com.lm.bos.web.action.processdefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {
	
	//ע��service
	@Autowired
	private RepositoryService repositoryService;
	
	/**
	 * ��ѯ���°汾�ĵ����̶����б�����
	 */
	public String list() {
		//������ѯ����
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.latestVersion();	//��ѯ���°汾
		query.orderByProcessDefinitionName().desc();	//�������̶������ƽ�������
		List<ProcessDefinition> list = query.list();	//ִ�в�ѯ
		//ѹջ
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	/**
	 * �������̶���
	 */
	//���մ��ݹ������ļ�
	private File zipFile;
	public void setZipFile(File zipFile) {
		this.zipFile = zipFile;
	}
	
	public String deploy() throws FileNotFoundException {
		//�������̲������
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		//��ȡzip�ļ���ȡ��
		deploymentBuilder.addZipInputStream(new ZipInputStream(new FileInputStream(zipFile)));
		//����
		deploymentBuilder.deploy();
		return "toList";
	}
	
	/**
	 * �鿴����ͼƬ
	 */
	//���մ��ݹ�����id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String showpng() {
		//ͨ��id��ȡpngͼƬ��Ӧ��������
		InputStream pngstream = repositoryService.getProcessDiagram(id);
		//ѹջ
		ActionContext.getContext().getValueStack().set("pngstream", pngstream);
		return "showpng";
	}
	
	/**
	 * ɾ�����̲���
	 */
	public String delete() {
		//�����Ƿ�ɾ���ɹ�
		String deltag = "1";	//1-�ɹ�;0-ʧ��
		//�������̶���id��ѯ���̲���id
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.processDefinitionId(id);	//�������̶���id����
		ProcessDefinition processDefinition = query.singleResult();
		String deploymentId = processDefinition.getDeploymentId();
		try {
			repositoryService.deleteDeployment(deploymentId);
		} catch (Exception e) {
			e.printStackTrace();
			//ɾ��ʧ��
			deltag = "0";
			//����ֵջ
			ActionContext.getContext().getValueStack().set("deltag", deltag);
			//�����б����²�ѯ
			this.list();
		}
		return "toList";
	}
}
