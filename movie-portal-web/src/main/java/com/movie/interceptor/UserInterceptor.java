package com.movie.interceptor;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.movie.actionimpl.UserAction;
import com.movie.actionimpl.ValidateAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@Component
public class UserInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		if (invocation.getAction() instanceof UserAction && ServletActionContext.getRequest().getSession().getAttribute("user") != null
				|| invocation.getAction() instanceof ValidateAction) {
			return invocation.invoke();
		}else {
			return "false";
		}
	}


}
