package com.lm.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lm.bos.dao.base.IBaseDao;
import com.lm.bos.utils.PageBean;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	//sessionFactory需要spring依赖注入
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	//通过构造方法获取T实际代表的类
	private Class<T> entityClass;
	public BaseDaoImpl() {
		//获取父类BaseDaoImpl<T>
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//得到T数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
		
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
		
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
		
	}

	@Override
	public T findById(Serializable id) {
		
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		String hql = "FROM " + entityClass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public void executeUpdate(String queryName, Object... objects) {
		//获取当前线程绑定的session对象
		Session session = this.getSession();
		//通过查询方法名获取query对象
		Query query = session.getNamedQuery(queryName);
		//为query的HQL语句进行赋值
		int i = 0 ;
		for (Object object : objects) {
			query.setParameter(i++, object);
		}
		//执行update方法
		query.executeUpdate();
		
	}

	//根据离线条件分页查询
	@Override
	public void queryPage(PageBean pageBean) {
		//查询总记录数total--select count(*)...
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//hibernate框架默认发送select * from ...语句,所以需要自定义hibernate发送的sql
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> listTotal = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		//封装进pagebean
		pageBean.setTotal(listTotal.get(0).intValue());
		
		//查询详细信息rows--由于自定义了hibernate框架的sql和表对象映射关系,所以需要重置
		detachedCriteria.setProjection(null);
		//重置表对象映射关系
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		
		int firstResult = (pageBean.getCurrentPage() - 1) * pageBean.getPageSize();	//要查询的第一个结果下标
		int maxResults = pageBean.getPageSize();									//要查询多少个结果
		List<Object> listRows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		//封装进pagebean
		pageBean.setRows(listRows);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
		
	}

}
