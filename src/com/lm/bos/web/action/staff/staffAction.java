package com.lm.bos.web.action.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lm.bos.domain.BcStaff;
import com.lm.bos.service.IStaffService;
import com.lm.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class staffAction extends BaseAction<BcStaff> {

	@Autowired
	private IStaffService staffService;
	
	/**
	 * Ôö¼Óstaff
	 */
	public String addStaff() {
		staffService.save(this.getModel());
		return "list";
	}
}
