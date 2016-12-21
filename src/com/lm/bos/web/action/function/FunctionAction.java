package com.lm.bos.web.action.function;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.AuthFunction;
import com.lm.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<AuthFunction> {

	/**
	 * ��ѯ��ҳ
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		//authfunction��Ҳ����page����,struts������ע��ģ������
		pageBean.setCurrentPage(Integer.parseInt(this.getModel().getPage()));
		functionService.queryPage(pageBean);
		this.writePageBean2Json(pageBean, new String[]{"authFunctions","authFunction","authRoles","currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
}
