package com.wickey.course.service;

import java.util.Map;

public class CropLocationService {
	
	
	public String getCropLoca(Map<String,String> requestMap) throws Exception{
		String respMessage = null;
		//发送方账号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		//公众账号
		String toUserName = requestMap.get("ToUserName");
		//消息类型
		String msgType = requestMap.get("MsgType");
		//纬度
		String latitude = requestMap.get("Latitude");
		//经度
		String longitude = requestMap.get("Longitude");
		//精度
		String precision = requestMap.get("Precision");
		
		
		
		
		String respContent = "";
		
		
		
		return null;
	}
}
