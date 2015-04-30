package com.wickey.course.message.req;

/**
 * 文本信息
 * 
 * @author fatboyliang
 * @date 2015-01-30
 */

public class TextMessage extends BaseMessage{
	//消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}
