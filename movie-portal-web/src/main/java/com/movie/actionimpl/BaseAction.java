package com.movie.actionimpl;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author zjt
 * @Description: 通用的操作放在这里
 * @param <T>
 */
@Controller
@Scope("prototype")
public class BaseAction<T> extends ActionSupport  implements RequestAware,ApplicationAware,SessionAware,ModelDriven<T>{

	protected T model;
	protected Map<String, Object> request;
	protected Map<String, Object> session;
	protected Map<String, Object> application;

	@SuppressWarnings("unchecked")
	@Override
	public T getModel() {
		ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
		Class<?> clazz = (Class<?>)type.getActualTypeArguments()[0];	
		try {
			model = (T)clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
		return model;
	}
	
	
	protected int getPageNum(int count,int pageNum,int num){
		if (count % num == 0) {
			pageNum = count/num;
		}else {
			pageNum = count/num + 1;
		}
		return pageNum;
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;

	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;

	}
}
