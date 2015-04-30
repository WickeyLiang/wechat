package com.wickey.course.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.wickey.course.bean.userbean.OAuth2UserInfo;

public class OAuth2Action extends ActionSupport{
	
	
	public void getUserInfo() throws Exception{
		
		OAuth2UserInfo oauth2UserInfo = new OAuth2UserInfo();
		
		
		
		
		
	}
	
	
	public void SendMsg(String msg) throws Exception{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(msg);
		
	}
}
