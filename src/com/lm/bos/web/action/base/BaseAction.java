package com.lm.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.lm.bos.crm.CustomerService;
import com.lm.bos.domain.BcRegion;
import com.lm.bos.domain.BcStaff;
import com.lm.bos.service.IDecidedzoneService;
import com.lm.bos.service.IFunctionService;
import com.lm.bos.service.INoticebillService;
import com.lm.bos.service.IRegionService;
import com.lm.bos.service.IRoleService;
import com.lm.bos.service.IStaffService;
import com.lm.bos.service.ISubareaService;
import com.lm.bos.service.IUserService;
import com.lm.bos.utils.PageBean;
import com.lm.crm.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	//service
	@Autowired
	protected IRegionService regionService;
	
	@Autowired
	protected IStaffService staffService;
	
	@Resource
	protected IUserService userService;
	
	@Autowired
	protected ISubareaService subareaService;
	
	@Autowired
	protected IDecidedzoneService decidedzoneService;
	
	@Autowired
	protected CustomerService customerService;
	
	@Autowired
	protected INoticebillService noticebillService;
	
	@Autowired
	protected IFunctionService functionService;
	
	@Autowired
	protected IRoleService roleService;


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
		ParameterizedType genericSuperclass = null;
		//�ж�this�Ƿ�Ϊcglib����
		if (this.getClass().getGenericSuperclass() instanceof ParameterizedType) {
			//����
			genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		} else {
			//��
			genericSuperclass = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
		}
		
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
		//���˵�����Ҫ��װ������
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		
		//����java.sql.Date��Ӧ��ת������ (sql.dateת����util.date)
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class,new JsonValueProcessor(){  
            @Override  
            public Object processArrayValue(Object value, JsonConfig config) {  
                return process(value);  
            }  
              
            @Override  
            public Object processObjectValue(String arg0, Object value,  
                    JsonConfig arg2) {  
                return process(value);  
            }  
              
            private Object process(Object value) {  
                try {  
                      
                    if (value instanceof java.sql.Date) {  
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                        return sdf.format( value);  
                    }  
                    return value == null ? "" : value.toString();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                    return "";  
                }  

            }  
        });  
		
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
	
	/**
	 * ��Object�����װ��json
	 * @param customer
	 * @param strings
	 * @throws IOException 
	 */
	public void writeObject2Json(Object obj, String[] excludes) throws IOException {
		//���˵�����Ҫ��װ������
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		
		JSONObject jsonObject = JSONObject.fromObject(obj, jsonConfig);
		String data = jsonObject.toString();
		
		//��json��ʽ��data����ҳ��
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(data);
		
	}

}
