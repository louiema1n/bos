package com.lm.bos.dao.base;

import java.io.Serializable;
import java.util.List;

/**
 * 抽取持久层通用方法
 * @author xtztx
 * @param <T>
 *
 */
public interface IBaseDao<T> {
	public void save(T entity);
	
	public void delete(T entity);
	
	public void update(T entity);
	
	public T findById(Serializable id);
	
	public List<T> findAll();
}
