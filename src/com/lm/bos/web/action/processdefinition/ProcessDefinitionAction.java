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
	
	//注入service
	@Autowired
	private RepositoryService repositoryService;
	
	/**
	 * 查询最新版本的的流程定义列表数据
	 */
	public String list() {
		//创建查询对象
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.latestVersion();	//查询最新版本
		query.orderByProcessDefinitionName().desc();	//根据流程定义名称降序排列
		List<ProcessDefinition> list = query.list();	//执行查询
		//压栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	/**
	 * 部署流程定义
	 */
	//接收传递过来的文件
	private File zipFile;
	public void setZipFile(File zipFile) {
		this.zipFile = zipFile;
	}
	
	public String deploy() throws FileNotFoundException {
		//创建流程部署对象
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		//获取zip文件读取流
		deploymentBuilder.addZipInputStream(new ZipInputStream(new FileInputStream(zipFile)));
		//部署
		deploymentBuilder.deploy();
		return "toList";
	}
	
	/**
	 * 查看流程图片
	 */
	//接收传递过来的id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String showpng() {
		//通过id获取png图片对应的输入流
		InputStream pngstream = repositoryService.getProcessDiagram(id);
		//压栈
		ActionContext.getContext().getValueStack().set("pngstream", pngstream);
		return "showpng";
	}
	
	/**
	 * 删除流程部署
	 */
	public String delete() {
		//定义是否删除成功
		String deltag = "1";	//1-成功;0-失败
		//根据流程定义id查询流程部署id
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.processDefinitionId(id);	//根据流程定义id过滤
		ProcessDefinition processDefinition = query.singleResult();
		String deploymentId = processDefinition.getDeploymentId();
		try {
			repositoryService.deleteDeployment(deploymentId);
		} catch (Exception e) {
			e.printStackTrace();
			//删除失败
			deltag = "0";
			//放入值栈
			ActionContext.getContext().getValueStack().set("deltag", deltag);
			//返回列表重新查询
			this.list();
		}
		return "toList";
	}
}
