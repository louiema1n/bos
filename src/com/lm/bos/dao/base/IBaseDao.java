package com.lm.bos.dao.base;

import java.io.Serializable;
import java.util.List;

/**
 * ��ȡ�־ò�ͨ�÷���
 * @author ly
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	/**
	 * ����
	 * @param entity
	 */
	public void save(T entity);
	
	/**
	 * ɾ��
	 * @param entity
	 */
	public void delete(T entity);
	
	/**
	 * �޸�
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * ����id��ѯ
	 * @param id
	 * @return
	 */
	public T findById(Serializable id);
	
	/**
	 * ��������
	 * @return
	 */
	public List<T> findAll();
}
