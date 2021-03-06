package com.wickey.course.message.resp;
/**
 * 
 * 消息基类（公众账号 -> 普通用户）
 * @author fatboyliang
 * @date 2015-01-30
 */
public class BaseMessage {
	//接收方账号（收到的OpenID）
	private String ToUserName;
	
	private String FromUserName;
	
	private long CreateTime;
	
	private String MsgType;
	//被标志时，星标刚收到的消息
	private int FuncFlag;
	//企业应用id，企业号才有
	private String AgentID;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public int getFuncFlag() {
		return FuncFlag;
	}
	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}
	public String getAgentID() {
		return AgentID;
	}
	public void setAgentID(String agentID) {
		AgentID = agentID;
	}

}
