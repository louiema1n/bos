package com.lm.bos.dao.function.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lm.bos.dao.base.impl.BaseDaoImpl;
import com.lm.bos.dao.function.IFunctionDao;
import com.lm.bos.domain.AuthFunction;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<AuthFunction> implements IFunctionDao {

	@Override
	public List<AuthFunction> findListById(String id) {
		// distinct»•÷ÿ
		String hql = "SELECT DISTINCT f FROM AuthFunction f LEFT OUTER JOIN f.authRoles r LEFT OUTER JOIN r.users u WHERE u.id = ? ";
		return this.getHibernateTemplate().find(hql, id);
	}

	@Override
	public List<AuthFunction> findAllMenu() {
		String hql = "FROM AuthFunction f WHERE f.generatemenu = '1' ORDER BY f.zindex DESC ";
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public List<AuthFunction> findMenuById(String id) {
		String hql = "SELECT DISTINCT f FROM AuthFunction f LEFT OUTER JOIN f.authRoles r LEFT OUTER JOIN r.users u WHERE u.id = ? AND f.generatemenu = '1' ORDER BY f.zindex DESC  ";
		return this.getHibernateTemplate().find(hql, id);
	}

}
