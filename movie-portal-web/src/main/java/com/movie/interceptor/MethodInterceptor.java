package com.movie.interceptor;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * @author zjt
 * @Description: 实现方法的拦截 
 */
@Component
public class MethodInterceptor extends MethodFilterInterceptor{

	private static final long serialVersionUID = 1L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		
		//登录了可以执行
		if (ServletActionContext.getRequest().getSession().getAttribute("user") != null) {
			String result = invocation.invoke();
			return result;
		}else {
			return "nologin";
		}
	}

}
