package com.lm.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.lm.bos.domain.BcRegion;
import com.lm.bos.domain.BcStaff;
import com.lm.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	//封装pagebean
	protected PageBean pageBean = new PageBean();
	//jsp传过来的分页查询数据,属性驱动自动封装数据
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	
	//指定detachedCriteria的对象
	DetachedCriteria detachedCriteria = null;
	
	private T model;
	@Override
	public T getModel() {
		return model;
	}
	//在构造方法中动态获取T实际代表的类型
	public BaseAction() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		//获得T
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		
		//将要查询的对象给detachedCriteria
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		//new T();通过反射获得模型对象
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用将pagebean封装成json数据方法
	 * @throws IOException 
	 */
	public void writePageBean2Json(PageBean pageBean, String[] excludes) throws IOException {
		//用json进行数据封装
		//过滤掉不需要封装的数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
		String data = jsonObject.toString();
		
		//将json格式的data返回页面
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(data);
	}
	
	/**
	 * 通用将List集合封装成json数据方法
	 * @throws IOException 
	 */
	public void writeList2Json(List allRegion, String[] excludes) throws IOException {
		//用json进行数据封装
		//过滤掉不需要封装的数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		
		JSONArray jsonArray = JSONArray.fromObject(allRegion, jsonConfig);
		String data = jsonArray.toString();
		
		//将json格式的data返回页面
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(data);
	}

}
