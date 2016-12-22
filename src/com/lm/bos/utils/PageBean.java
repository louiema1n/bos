package com.lm.bos.utils;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
/**
 * 分页查询相关信息
 * @author xtztx
 *
 */
public class PageBean {
	//页面传递过来的
	private int currentPage=1;	//当前页
	private int pageSize=1;		//每页显示记录数
	private DetachedCriteria detachedCriteria ;		//离线查询条件
	
	//需要根据条件向数据库查询的
	private int total;	//总记录数
	private List<Object> rows;	//查询到的详细记录
	
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
