package com.qiyuan.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.qiyuan.common.CommonUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptorAction extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6993098731302572901L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		// 不做该项拦截
		if (action instanceof LoginAction) {
			return invocation.invoke();
		}
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		String userid = String.valueOf(session.get("userId"));
		if (CommonUtils.parseInt(userid, 0) > 0) {
			return invocation.invoke();
		}
		if (isAjaxRequest(request)) {
			setResponseStr("sessionerror");
			return null;
		} else {
			return "sessionnull";
		}
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}

	public void setResponseStr(String responseString) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(responseString);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
