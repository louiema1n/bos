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
	 * 根据telephone查询customers
	 * @throws IOException 
	 */
	public String queryCustomerByTelephone() throws IOException {
		Customer customer = customerService.findCustomerByTel(this.getModel().getTelephone());
		this.writeObject2Json(customer, new String[]{});
		return NONE;
	}
	
	/**
	 * 新增业务单
	 */
	public String add() {
		//获取业务员userid
		User user = BOSContext.getLoginUser();
		this.getModel().setUserId(user.getId());
		//保存业务通知单
		noticebillService.save(this.getModel());
		return "add";
	}

}
