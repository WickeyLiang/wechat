package com.wickey.course.util;

import org.apache.log4j.Logger;

import com.wickey.course.bean.paramsAPI.ParaAPI;
import com.wickey.course.bean.pojo.AccessToken;

public class TokenThread implements Runnable{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public static String appid = ParaAPI.corpId;
	public static String appsecret = ParaAPI.secret;
	public static AccessToken accessToken = null;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				accessToken = WeixinUtil.getAccessToken(appid, appsecret);
				if(null != accessToken){
					logger.info("获取access_token成功，有效时长{"+accessToken.getExpiresIn()+"} token:"+accessToken.getToken());
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
					
				}else{
					Thread.sleep(60 * 1000); 
				}
			} catch (Exception e) {
				// TODO: handle exception
				try {  
                    Thread.sleep(60 * 1000);  
                } catch (InterruptedException e1) {  
                    logger.error("{}", e1);  
                }  
                logger.error("{}", e); 
			}
		}
		
	}

}
