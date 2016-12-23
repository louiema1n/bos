package com.lm.bos.web.action.noticebill;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.QpNoticebill;
import com.lm.bos.domain.User;
import com.lm.bos.utils.BOSContext;
import com.lm.bos.web.action.base.BaseAction;
import com.lm.crm.domain.Customer;

@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<QpNoticebill> {
	
	/**
	 * ����telephone��ѯcustomers
	 * @throws IOException 
	 */
	public String queryCustomerByTelephone() throws IOException {
		Customer customer = customerService.findCustomerByTel(this.getModel().getTelephone());
		this.writeObject2Json(customer, new String[]{});
		return NONE;
	}
	
	/**
	 * ����ҵ��
	 */
	public String add() {
		//��ȡҵ��Աuserid
		User user = BOSContext.getLoginUser();
		this.getModel().setUserId(user.getId());
		//����ҵ��֪ͨ��
		noticebillService.save(this.getModel());
		return "add";
	}

}
