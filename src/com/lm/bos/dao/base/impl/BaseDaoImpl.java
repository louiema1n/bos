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

	//sessionFactory��Ҫspring����ע��
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	//ͨ�����췽����ȡTʵ�ʴ������
	private Class<T> entityClass;
	public BaseDaoImpl() {
		//��ȡ����BaseDaoImpl<T>
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//�õ�T����
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
		//��ȡ��ǰ�̰߳󶨵�session����
		Session session = this.getSession();
		//ͨ����ѯ��������ȡquery����
		Query query = session.getNamedQuery(queryName);
		//Ϊquery��HQL�����и�ֵ
		int i = 0 ;
		for (Object object : objects) {
			query.setParameter(i++, object);
		}
		//ִ��update����
		query.executeUpdate();
		
	}

	//��������������ҳ��ѯ
	@Override
	public void queryPage(PageBean pageBean) {
		//��ѯ�ܼ�¼��total--select count(*)...
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//hibernate���Ĭ�Ϸ���select * from ...���,������Ҫ�Զ���hibernate���͵�sql
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> listTotal = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		//��װ��pagebean
		pageBean.setTotal(listTotal.get(0).intValue());
		
		//��ѯ��ϸ��Ϣrows--�����Զ�����hibernate��ܵ�sql�ͱ����ӳ���ϵ,������Ҫ����
		detachedCriteria.setProjection(null);
		//���ñ����ӳ���ϵ
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		
		int firstResult = (pageBean.getCurrentPage() - 1) * pageBean.getPageSize();	//Ҫ��ѯ�ĵ�һ������±�
		int maxResults = pageBean.getPageSize();									//Ҫ��ѯ���ٸ����
		List<Object> listRows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		//��װ��pagebean
		pageBean.setRows(listRows);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
		
	}

}
