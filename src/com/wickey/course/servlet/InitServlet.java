package com.wickey.course.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3385155128032615248L;
	
	private static Logger log = LoggerFactory.getLogger(InitServlet.class);
	
	
	public void init() throws ServletException{
		log.info("创建菜单中~~~~");
		new MenuManager().init();
	}
	
}
