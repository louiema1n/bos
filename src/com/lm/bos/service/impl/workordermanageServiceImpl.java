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
		//��������ʵ��,�������̱���,�޸Ĺ�������startΪ1
		//ͨ��id��ѯ��qpworkordermanage����
		QpWorkordermanage workordermanage = workordermanageDao.findById(id);
		workordermanage.setStart("1");
		
		//�������̱���
		Map<String, Object> variables = new HashMap<>();
		variables.put("ҵ������", workordermanage);	//Ĭ��tostring��������д
		
		//��������ʵ��
		String processDefinitionKey = "transfer";
		String businesskey = id;	//ҵ������--ҵ�������ֵ---�ù��������ҵ�ҵ������
		runtimeService.startProcessInstanceByKey(processDefinitionKey, businesskey, variables);
	}

}
