package com.lm.bos.web.action.decidedzone;

import java.io.IOException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.BcDecidedzone;
import com.lm.bos.web.action.base.BaseAction;
import com.lm.crm.domain.Customer;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<BcDecidedzone> {

	//���շ���id
	private String[] bcSubareaid;
	public void setBcSubareaid(String[] bcSubareaid) {
		this.bcSubareaid = bcSubareaid;
	}

	/**
	 * ��Ӷ���
	 */
	public String add() {
		decidedzoneService.save(this.getModel(), bcSubareaid);
		return "list";
	}
	
	/**
	 * ��ҳ��ѯ
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		decidedzoneService.queryPage(pageBean);
		this.writePageBean2Json(pageBean, new String[]{"bcSubareas", "bcDecidedzones", "currentPage", "detachedCriteria", "pageSize"});
		return NONE;
	}
	
	/**
	 * ��ѯδ���������Ŀͻ�
	 * @throws IOException 
	 */
	public String findnoassociationCustomers() throws IOException {
		List<Customer> list = customerService.findnoassociationCustomers();
		writeList2Json(list, new String[] {"station","telephone","address","decidedzone_id"});
		return NONE;
	}
	
	/**
	 * ��ѯ�ѹ��������Ŀͻ�
	 * @throws IOException 
	 */
	//����decidedzoneid
	private String decidedzoneid;
	public void setDecidedzoneid(String decidedzoneid) {
		this.decidedzoneid = decidedzoneid;
	}

	public String findassociationCustomers() throws IOException {
		List<Customer> list = customerService.findhasassociationCustomers(decidedzoneid);
		writeList2Json(list, new String[] {"station","telephone","address","decidedzone_id"});
		return NONE;
	}
	
	/**
	 * ���������ͻ�
	 */
	//���տͻ�id
	private Integer[] customerIds;
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}

	public String assigncustomerstodecidedzone() {
		customerService.assignCustomersToDecidedZone(customerIds, this.getModel().getId());
		return "list";
	}
}
