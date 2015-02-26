package com.wickey.course.servlet;



import org.apache.log4j.Logger;

import com.wickey.course.bean.pojo.AccessToken;
import com.wickey.course.bean.pojo.Button;
import com.wickey.course.bean.pojo.CommonButton;
import com.wickey.course.bean.pojo.ComplexButton;
import com.wickey.course.bean.pojo.Menu;
import com.wickey.course.util.WeixinUtil;

public class MenuManager {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	public void init(){
		//第三方用户唯一凭证
		//String appId = "wx26370891564f6f7f";
		String appId = "wx668290fdbd99f4d2";
		//第三方用户唯一凭证密钥
		//String appSecret = "74e670576d6abbcd5d40f45d758a4490";
		String appSecret = "0a721e7a3751fa33525ac0c2aac855d1";
		//调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
		
		if(null != at){
			//调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());
			
			//判断菜单创建结果
			if(0 == result){
				logger.info("菜单创建成功！");
			}else{
				logger.info("菜单创建失败，错误码："+ result);
			}
		}
				
	}
	
	private static Menu getMenu(){
		CommonButton btn11 = new CommonButton();
		btn11.setName("天气预报");
		btn11.setType("click");
		btn11.setKey("11");
		
		CommonButton btn12 = new CommonButton();
		btn12.setName("公交查询");
		btn12.setType("click");
		btn12.setKey("12");
		
		CommonButton btn13 = new CommonButton();
		btn13.setName("我在哪");
		btn13.setType("click");
		btn13.setKey("13");
		
		CommonButton btn21 = new CommonButton();
		btn21.setName("我要点歌");
		btn21.setType("click");
		btn21.setKey("21");
		
		CommonButton btn31 = new CommonButton();
		btn31.setName("关于我");
		btn31.setType("click");
		btn31.setKey("31");
		
		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("生活助手");
		mainBtn1.setSub_button(new CommonButton[]{btn11,btn12});
		
		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("轻松娱乐");
		mainBtn2.setSub_button(new CommonButton[]{btn21});
		
		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("帮助");
		mainBtn3.setSub_button(new CommonButton[]{btn31});
		
		Menu menu = new Menu();
		menu.setButton(new Button[]{mainBtn1,mainBtn2,mainBtn3});
		
		return menu;
	}
	
	
	
}
