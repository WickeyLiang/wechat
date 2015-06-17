package com.wickey.course.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.wickey.course.bean.userbean.OAuth2UserInfo;

@Controller
public class OAuth2Action extends ActionSupport{
	
	private String code;
	private String state;
	
	
	public void getUserInfo() throws Exception{
		
		System.out.println("进来了");
		System.out.println(code);
		System.out.println(state);
		
		
		
		
	}
	
	
	public void SendMsg(String msg) throws Exception{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(msg);
		
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}
}
