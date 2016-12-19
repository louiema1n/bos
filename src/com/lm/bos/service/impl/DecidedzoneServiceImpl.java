package com.lm.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.decidedzone.IDecidedzoneDao;
import com.lm.bos.dao.subarea.ISubareaDao;
import com.lm.bos.domain.BcDecidedzone;
import com.lm.bos.domain.BcSubarea;
import com.lm.bos.service.IDecidedzoneService;
import com.lm.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {
	
	@Resource
	private IDecidedzoneDao decidedzoneDao;
	
	@Resource
	private ISubareaDao subareaDao;

	@Override
	public void save(BcDecidedzone model, String[] sid) {
		decidedzoneDao.save(model);
		//�����Է�����һ�Զ�,��Ҫ��.hbm.xml�ļ������������ϵά���ķ���
		//һ��һ�������������ϵ��ά��
		for (String id : sid) {
			//������ѯ��subarea-->�־û�����
			BcSubarea subarea = subareaDao.findById(id);
			//�ɶ��һ����ά�������ϵ
			subarea.setBcDecidedzone(model); 	//����,�Զ�����sql
		}
		
	}

	@Override
	public void queryPage(PageBean pageBean) {
		decidedzoneDao.queryPage(pageBean);
		
	}

}
