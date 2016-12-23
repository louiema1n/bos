package com.lm.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm.bos.dao.staff.IStaffDao;
import com.lm.bos.domain.BcStaff;
import com.lm.bos.service.IStaffService;
import com.lm.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

	@Autowired
	private IStaffDao staffDao;
	
	@Override
	public void save(BcStaff model) {
		staffDao.save(model);
	}
	
	@Override
	public void queryPage(PageBean pageBean) {
		staffDao.queryPage(pageBean);
		
	}

	@Override
	public void deleteBatch(String ids) {
		//对ids进行拆分
		String[] staffIds = ids.split(",");
		//遍历,执行update语句
		for (String id : staffIds) {
			staffDao.executeUpdate("deleteStaff", id);
		}
		
	}

	@Override
	public BcStaff query(BcStaff model) {
		return staffDao.findById(model.getId());
	}

	@Override
	public void update(BcStaff oldStaff) {
		staffDao.update(oldStaff);
	}

	@Override
	public List<BcStaff> findByDeltag() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BcStaff.class);
		detachedCriteria.add(Restrictions.eq("deltag", '1'));	//eq相等;ne不相等
		return staffDao.findByCriteria(detachedCriteria);
	}

}
