package com.lm.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.lm.bos.domain.BcRegion;
import com.lm.bos.domain.BcStaff;
import com.lm.bos.utils.PageBean;

/**
 * 抽取持久层通用方法
 * @author xtztx
 * @param <T>
 *
 */
public interface IBaseDao<T> {
	public void save(T entity);
	
	/**
	 * id存在就update,不存在就insert
	 * @param entity
	 */
	public void saveOrUpdate(T entity);
	
	public void delete(T entity);
	
	public void update(T entity);
	
	public T findById(Serializable id);
	
	public List<T> findAll();
	
	/**
	 * 根据查询方法名称和相应的参数更新用户信息的通用方法
	 * 
	 * @param queryName--在*.hbm.xml中配置
	 * @param ...objects
	 */
	public void executeUpdate(String queryName, Object ...objects);
	
	/**
	 * 分页查询
	 * @param pageBean
	 */
	public void queryPage(PageBean pageBean);
	
	/**
	 * 通用条件方法
	 */
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
}
