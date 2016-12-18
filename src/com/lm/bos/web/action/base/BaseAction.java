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

	//��װpagebean
	protected PageBean pageBean = new PageBean();
	//jsp�������ķ�ҳ��ѯ����,���������Զ���װ����
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	
	//ָ��detachedCriteria�Ķ���
	DetachedCriteria detachedCriteria = null;
	
	private T model;
	@Override
	public T getModel() {
		return model;
	}
	//�ڹ��췽���ж�̬��ȡTʵ�ʴ��������
	public BaseAction() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		//���T
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		
		//��Ҫ��ѯ�Ķ����detachedCriteria
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		//new T();ͨ��������ģ�Ͷ���
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ͨ�ý�pagebean��װ��json���ݷ���
	 * @throws IOException 
	 */
	public void writePageBean2Json(PageBean pageBean, String[] excludes) throws IOException {
		//��json�������ݷ�װ
		//���˵�����Ҫ��װ������
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
		String data = jsonObject.toString();
		
		//��json��ʽ��data����ҳ��
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(data);
	}
	
	/**
	 * ͨ�ý�List���Ϸ�װ��json���ݷ���
	 * @throws IOException 
	 */
	public void writeList2Json(List allRegion, String[] excludes) throws IOException {
		//��json�������ݷ�װ
		//���˵�����Ҫ��װ������
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		
		JSONArray jsonArray = JSONArray.fromObject(allRegion, jsonConfig);
		String data = jsonArray.toString();
		
		//��json��ʽ��data����ҳ��
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(data);
	}

}
