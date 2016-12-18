package com.lm.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import com.lm.bos.domain.BcRegion;
import com.lm.bos.utils.PageBean;

/**
 * ��ȡ�־ò�ͨ�÷���
 * @author xtztx
 * @param <T>
 *
 */
public interface IBaseDao<T> {
	public void save(T entity);
	
	/**
	 * id���ھ�update,�����ھ�insert
	 * @param entity
	 */
	public void saveOrUpdate(T entity);
	
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
	
	/**
	 * ��ҳ��ѯ
	 * @param pageBean
	 */
	public void queryPage(PageBean pageBean);
}
