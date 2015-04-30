package com.wickey.course.demo;

import java.util.List;

import com.wickey.course.bean.pojo.AccessToken;
import com.wickey.course.util.TokenThread;
import com.wickey.course.util.WeixinUtil;

public class RunDemo2 {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccessToken at = new AccessToken();
		at.setExpiresIn(7200);
		at.setToken("dtiA1yIYpMUIEa3225f45UxNYcA_oPTzeq32tKhGXB-6U56Q1RAnLi6dy6-XK4O7bxrE8EpmdDiibFgo7V_dOwOE4aOcxGJm4uWuLXf4EgM");
		WeixinUtil wxu = new WeixinUtil();
		List openidList = wxu.getOpenID(at.getToken());
		wxu.getUserInfo(at.getToken(), openidList.get(0).toString());
		
	}

}
