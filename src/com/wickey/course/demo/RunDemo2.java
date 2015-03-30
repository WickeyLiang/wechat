package com.wickey.course.demo;

import com.wickey.course.bean.pojo.AccessToken;
import com.wickey.course.util.TokenThread;
import com.wickey.course.util.WeixinUtil;

public class RunDemo2 {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccessToken at = new AccessToken();
		at.setExpiresIn(7200);
		at.setToken("5-oMbE7Ps-94t0GPpoY6aVUUwRPiOZQnrhIi-GO57kkLmLhzCASKZx_38wXpKUHU");
		WeixinUtil wxu = new WeixinUtil();
		wxu.getUserInfo(at.getToken());
	}

}
