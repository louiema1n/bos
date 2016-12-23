package com.lm.bos.web.action.function;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.dao.function.IFunctionDao;
import com.lm.bos.domain.AuthFunction;
import com.lm.bos.domain.User;
import com.lm.bos.utils.BOSContext;
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
	
	/**
	 * ��ѯ����function
	 * @throws IOException 
	 */
	public String listajax() throws IOException {
		List<AuthFunction> list = functionService.findAll();
		this.writeList2Json(list, new String[] {"authFunctions","authRoles","authFunction"});
		return NONE;
	}
	
	/**
	 * ����function
	 */
	public String add() {
		//���authFunction��Ϊ�գ���pidΪ""���轫authFunction��Ϊ��
		if (this.getModel().getAuthFunction() != null && this.getModel().getAuthFunction().getId().equals("")) {
			this.getModel().setAuthFunction(null);
		}
		functionService.add(this.getModel());
		return "list";
	}
	
	/**
	 * ���ز˵�
	 */
	@Resource
	private IFunctionDao functionDao;
	public String findMenu() throws IOException {
		//��ȡ��ǰ��¼�û�
		User loginUser = BOSContext.getLoginUser();
		List<AuthFunction> list = null;
		if (loginUser.getUsername().equals("admin")) {
			//��������Ա,��ѯ���в˵�
			list = functionDao.findAllMenu();
		} else {
			//��ͨ�û�,��ѯid��Ӧ�˵�
			list = functionDao.findMenuById(loginUser.getId());
		}
		
		this.writeList2Json(list, new String[]{"authFunction","authFunctions","authRoles"});
		return NONE;
	}
}
