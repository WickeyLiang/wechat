package com.wickey.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.wickey.course.service.CoreService;
import com.wickey.course.util.SignUtil;
/**
 * 核心请求处理类
 * @author fatboyliang
 * @date 2015-01-15
 */
public class CoreServlet extends HttpServlet{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 确认请求来自微信服务器
	 * 
	 */
	private static final long serialVersionUID = 7813321705663820137L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		// 消息接收、处理、响应
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//微信加密签名
		String signature = request.getParameter("signature");
		logger.info("收到来自微信服务器的signature值为"+signature);
		//时间戳
		String timestamp = request.getParameter("timestamp");
		logger.info("收到来自微信服务器的timestamp值为"+timestamp);
		//随机数
		String nonce = request.getParameter("nonce");
		logger.info("收到来自微信服务器的nonce值为"+nonce);
		//随机字符串
		String echostr = request.getParameter("echostr");
		logger.info("收到来自微信服务器的echostr值为"+echostr);
		PrintWriter out = response.getWriter();
		//通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if(SignUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		out.close();
		out = null;
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		// 消息接收、处理、响应
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//调用核心业务接收消息、处理消息
		String respMessage = CoreService.processRequest(request);
		logger.info("发送给微信服务器的返回信息：\n"+respMessage);
		//System.out.println(respMessage);
		//响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}
}

