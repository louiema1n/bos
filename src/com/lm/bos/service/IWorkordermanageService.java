package com.lm.bos.service;

import java.util.List;

import com.lm.bos.domain.QpWorkordermanage;
import com.lm.bos.utils.PageBean;

public interface IWorkordermanageService {


	void add(QpWorkordermanage model);

	void querypage(PageBean pageBean);

	List<QpWorkordermanage> listNoStart();

	void start(String id);

	void checkWorkOrderManage(String workordermanageId, String processInstanceId, Integer check, String taskId);
	
}
