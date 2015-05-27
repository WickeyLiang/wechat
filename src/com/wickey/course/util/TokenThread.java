package com.wickey.course.util;

import org.apache.log4j.Logger;

import com.wickey.course.bean.paramsAPI.ParaAPI;
import com.wickey.course.bean.pojo.AccessToken;

public class TokenThread implements Runnable{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	//企业号
	//public static String appid = ParaAPI.corpId;
	//public static String appsecret = ParaAPI.secret;
	//测试号
	public static String appid = ParaAPI.appId_test;
	public static String appsecret = ParaAPI.appSecret_test;
	public static String flag = "test";

	boolean flag1 = true;
	public static AccessToken accessToken = null;
	
	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		while(true){
			try {
				accessToken = WeixinUtil.getAccessToken(appid, appsecret,flag);
				if(null != accessToken){
					logger.info("获取access_token成功，有效时长{"+accessToken.getExpiresIn()+"} token:"+accessToken.getToken());
					if(flag1){
						logger.info("创建菜单中~~~~");
						new MenuManager().init();
						flag1 = false;
					}
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
					//Thread.sleep(10000);
					
				}else{
					Thread.sleep(60 * 1000); 
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				try {  
                    Thread.sleep(60 * 1000);  
                } catch (InterruptedException e1) {  
                    logger.error(e1);  
                }  
                logger.error(e); 
			}
		}
		
		
		
	}

}
