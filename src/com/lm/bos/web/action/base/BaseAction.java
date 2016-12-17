package com.lm.bos.web.action.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

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
		//new T();ͨ��������ģ�Ͷ���
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
