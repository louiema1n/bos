package com.lm.bos.dao.base;

import java.io.Serializable;
import java.util.List;

/**
 * ��ȡ�־ò�ͨ�÷���
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
	
	/**
	 * ���ݲ�ѯ�������ƺ���Ӧ�Ĳ��������û���Ϣ��ͨ�÷���
	 * 
	 * @param queryName--��*.hbm.xml������
	 * @param ...objects
	 */
	public void executeUpdate(String queryName, Object ...objects);
}
