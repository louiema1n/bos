package com.lm.bos.utils;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
/**
 * ��ҳ��ѯ�����Ϣ
 * @author xtztx
 *
 */
public class PageBean {
	//ҳ�洫�ݹ�����
	private int currentPage=1;	//��ǰҳ
	private int pageSize=1;		//ÿҳ��ʾ��¼��
	private DetachedCriteria detachedCriteria ;		//���߲�ѯ����
	
	//��Ҫ�������������ݿ��ѯ��
	private int total;	//�ܼ�¼��
	private List<Object> rows;	//��ѯ������ϸ��¼
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}
	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}
	public List<Object> getRows() {
		return rows;
	}
	public void setRows(List<Object> rows) {
		this.rows = rows;
	}
	
	
}
