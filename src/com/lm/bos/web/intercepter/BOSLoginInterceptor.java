package com.lm.bos.web.intercepter;

import org.apache.struts2.ServletActionContext;

import com.lm.bos.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * �Զ����¼��֤������(��������������)
 * @author xtztx
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//��ȡsession�е�¼���û�
		User loginUser = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		//�ж��û��Ƿ����
		if (loginUser == null) {
			//�û�δ��¼
			return "login";
		}
		//�û��ѵ�¼,����
		return invocation.invoke();
	}

}
