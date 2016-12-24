package com.lm.bos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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

}
