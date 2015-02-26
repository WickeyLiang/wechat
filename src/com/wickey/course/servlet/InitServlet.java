package com.wickey.course.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;




public class InitServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3385155128032615248L;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public InitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	
	public void init() throws ServletException{
		//System.out.println("abcd");
		logger.info("创建菜单中~~~~");
		new MenuManager().init();
	}
	
}
