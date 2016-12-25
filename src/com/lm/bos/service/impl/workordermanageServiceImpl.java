package com.lm.bos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.workordermanage.IWorkordermanageDao;
import com.lm.bos.domain.QpWorkordermanage;
import com.lm.bos.service.IWorkordermanageService;
import com.lm.bos.utils.PageBean;

@Service
@Transactional
public class workordermanageServiceImpl implements IWorkordermanageService {
	@Resource
	private IWorkordermanageDao workordermanageDao;
	
	@Resource
	private RuntimeService runtimeService;
	
	@Resource
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Override
	public void add(QpWorkordermanage entity) {
		workordermanageDao.save(entity);
		
	}

	@Override
	public void querypage(PageBean pageBean) {
		workordermanageDao.queryPage(pageBean);
	}

	@Override
	public List<QpWorkordermanage> listNoStart() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(QpWorkordermanage.class);
		detachedCriteria.add(Restrictions.eq("start", "0"));
		return workordermanageDao.findByCriteria(detachedCriteria );
	}

	@Override
	public void start(String id) {
		//启动流程实例,设置流程变量,修改工作单中start为1
		//通过id查询出qpworkordermanage对象
		QpWorkordermanage workordermanage = workordermanageDao.findById(id);
		workordermanage.setStart("1");
		
		//设置流程变量
		Map<String, Object> variables = new HashMap<>();
		variables.put("业务数据", workordermanage);	//默认tostring方法被重写
		
		//启动流程实例
		String processDefinitionKey = "transfer";
		String businesskey = id;	//业务主键--业务表主键值---让工作流程找到业务数据
		runtimeService.startProcessInstanceByKey(processDefinitionKey, businesskey, variables);
	}

	/**
	 * 办理审核任务
	 */
	@Override
	public void checkWorkOrderManage(String workordermanageId, String processInstanceId, Integer check, String taskId) {
		//根据check审核任务
		Map<String, Object> variables = new HashMap<>();
		//将check放入流程变量
		variables.put("check", check);
		//办理任务
		taskService.complete(taskId, variables);
		if (check == 0) {
			//审核不通过,将workordermanage的start改为0
			QpWorkordermanage workordermanage = workordermanageDao.findById(workordermanageId);
			workordermanage.setStart("0");
			//删除历史流程实例记录--complete之后processInstanceId为空
			historyService.deleteHistoricProcessInstance(processInstanceId);
		}
		
	}

}
