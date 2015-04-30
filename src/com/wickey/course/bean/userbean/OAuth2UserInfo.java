package com.wickey.course.bean.userbean;

import java.util.List;

public class OAuth2UserInfo extends UserInfo{
	private List privilege;

	public List getPrivilege() {
		return privilege;
	}

	public void setPrivilege(List privilege) {
		this.privilege = privilege;
	}

	
}
