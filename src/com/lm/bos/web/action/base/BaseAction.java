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
		ParameterizedType genericSuperclass = null;
		//判断this是否为cglib代理
		if (this.getClass().getGenericSuperclass() instanceof ParameterizedType) {
			//不是
			genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		} else {
			//是
			genericSuperclass = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
		}
		
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
		//过滤掉不需要封装的数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		
		//设置java.sql.Date对应的转换方法 (sql.date转换成util.date)
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
	
	/**
	 * 将Object对象封装成json
	 * @param customer
	 * @param strings
	 * @throws IOException 
	 */
	public void writeObject2Json(Object obj, String[] excludes) throws IOException {
		//过滤掉不需要封装的数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		
		JSONObject jsonObject = JSONObject.fromObject(obj, jsonConfig);
		String data = jsonObject.toString();
		
		//将json格式的data返回页面
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(data);
		
	}

}
