package com.wickey.course.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetOAuthCodeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1436749105824496228L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		System.out.println(code);
		
	}
	
	
}
