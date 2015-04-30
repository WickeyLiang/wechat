package com.wickey.course.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;



import com.wickey.course.bean.paramsAPI.ParaAPI;
import com.wickey.course.service.CoreService;
import com.wickey.course.util.SignUtil;
import com.wickey.course.util.aes.AesException;
import com.wickey.course.util.aes.WXBizMsgCrypt;
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
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		/*
		String signature = request.getParameter("signature");
		logger.info("收到来自微信服务器的signature值为"+signature);*/
		//微信加密签名
		logger.info("msg_signature值为"+request.getParameter("msg_signature"));
		String msg_signature = request.getParameter("msg_signature");
		logger.info("收到来自微信服务器的msg_signature值为"+msg_signature);
		//时间戳
		String timestamp = request.getParameter("timestamp");
		logger.info("收到来自微信服务器的timestamp值为"+timestamp);
		//随机数
		String nonce = request.getParameter("nonce");
		logger.info("收到来自微信服务器的nonce值为"+nonce);
		//随机字符串
		String echostr = request.getParameter("echostr");
		// 打印请求地址
		logger.info("request=" + request.getRequestURL());
		logger.info("收到来自微信服务器的echostr值为"+echostr);
		PrintWriter out = response.getWriter();
		//通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		/*if(SignUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}*/
		String result = null;
		try {
			WXBizMsgCrypt wxmcpt = new WXBizMsgCrypt(ParaAPI.token_crop, ParaAPI.encodingAESKey_crop, ParaAPI.corpId);
			result = wxmcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
		} catch (AesException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if(result == null){
			result = ParaAPI.token;
		}
		String str = msg_signature + " " + timestamp + " " + nonce + " " + echostr;
		// 打印参数+地址+result
		logger.info("Exception:" + result + " " + request.getRequestURL() + " " + "FourParames:" + str);
		out.write(result);
		out.close();
		out = null;
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		// 消息接收、处理、响应
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	
		// 微信加密签名
		String msg_signature = request.getParameter("msg_signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");

		// 从请求中读取整个post数据
		InputStream inputStream = request.getInputStream();
		// commons.io.jar 方法
		String postData = IOUtils.toString(inputStream, "UTF-8");
		// Post打印结果
		logger.info("POST数据（解密前）："+postData);

		String Msg = "";
		WXBizMsgCrypt wxcpt = null;
		try {
			wxcpt = new WXBizMsgCrypt(ParaAPI.token_crop, ParaAPI.encodingAESKey_crop, ParaAPI.corpId);
			// 解密消息
			Msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, postData);
		} catch (AesException e) {
			e.printStackTrace();
		}
		// Msg打印结果
		logger.info("解密后的postData：" + Msg);
		
		
		//调用核心业务接收消息、处理消息
		String respMessage = CoreService.processRequest(Msg);
		logger.info("发送给微信服务器的返回信息：\n"+respMessage);
		String encryptMsg = "";
		//加密回复的消息
		try {
			encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);
		} catch (AesException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//响应消息
		PrintWriter out = response.getWriter();
		out.print(encryptMsg);
		out.close();
	}
}

