package com.lm.bos.web.action.decidedzone;

import java.io.IOException;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.BcDecidedzone;
import com.lm.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<BcDecidedzone> {

	//接收分区id
	private String[] bcSubareaid;
	public void setBcSubareaid(String[] bcSubareaid) {
		this.bcSubareaid = bcSubareaid;
	}

	/**
	 * 添加定区
	 */
	public String add() {
		decidedzoneService.save(this.getModel(), bcSubareaid);
		return "list";
	}
	
	/**
	 * 分页查询
	 * @throws IOException 
	 */
	public String queryPage() throws IOException {
		decidedzoneService.queryPage(pageBean);
		this.writePageBean2Json(pageBean, new String[]{"bcSubareas", "bcDecidedzones", "currentPage", "detachedCriteria", "pageSize"});
		return NONE;
	}
}
