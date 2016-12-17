package com.lm.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.staff.IStaffDao;
import com.lm.bos.domain.BcStaff;
import com.lm.bos.service.IStaffService;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

	@Autowired
	private IStaffDao staffDao;
	@Override
	public void save(BcStaff model) {
		staffDao.save(model);
	}

}
