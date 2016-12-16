package com.lm.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lm.bos.dao.base.IBaseDao;

public class IBaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	//ͨ�����췽����ȡT������ľ������
	private Class<T> entityClass;
	public IBaseDaoImpl() {
		//��ȡIBaseDaoImpl<T>������
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//��ø���T������
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
		
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
		
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
		
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {
		String hql = "from " + entityClass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

}
