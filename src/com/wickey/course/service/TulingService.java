package com.wickey.course.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.wickey.course.bean.paramsAPI.ParaAPI;
import com.wickey.course.bean.tulingbean.AirBean;
import com.wickey.course.bean.tulingbean.BaseBean;
import com.wickey.course.bean.tulingbean.HotelBean;
import com.wickey.course.bean.tulingbean.NewsBean;
import com.wickey.course.bean.tulingbean.PriceBean;
import com.wickey.course.bean.tulingbean.SoftWareDownLoadBean;
import com.wickey.course.bean.tulingbean.TrainBean;
import com.wickey.course.message.resp.Article;
import com.wickey.course.message.resp.NewsMessage;
import com.wickey.course.message.resp.TextMessage;
import com.wickey.course.util.JsonUtil;
import com.wickey.course.util.MessageUtil;
import com.wickey.course.util.TulingApiUtil;

public class TulingService {
	
	
	public String getTulingData(Map<String,String> requestMap) throws Exception{
		String respMessage = null;
		//发送方账号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		//公众账号
		String toUserName = requestMap.get("ToUserName");
		//消息类型
		String msgType = requestMap.get("MsgType");
		//消息内容
		String content = requestMap.get("Content");
		
		String respContent = "";
		//回复文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setAgentID(ParaAPI.agentId);
		
		
		JSONObject json = new TulingApiUtil().getTulingResult(content);
		if(100000==json.getInt("code")){
			respContent = json.getString("text");
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		}else if(200000==json.getInt("code")){
			respContent = json.getString("text")+"\n"+json.getString("url");
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		}else if(302000==json.getInt("code")){
			
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			int i = 1;
			List<Article> articleList = new ArrayList<Article>();
			List jsontolist = JsonUtil.JsonToListForTuling(json);
			Iterator it = jsontolist.iterator();
			//while(it.hasNext()){
			while(i<6){
				NewsBean nb = (NewsBean) it.next();
				Article article = new Article();
				article.setTitle(nb.getArticle());
				String description = "第"+i+"条新闻,来自"+nb.getSource()+"\n欢迎点击浏览详细内容！！";
				article.setDescription(description);
				article.setPicUrl(nb.getIcon());
				article.setUrl(nb.getDetailurl());
				articleList.add(article);
				i++;
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsMessage);
			return respMessage;
		}else if(308000==json.getInt("code")){
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			int i = 1;
			List<Article> articleList = new ArrayList<Article>();
			List jsontolist = JsonUtil.JsonToListForTuling(json);
			Iterator it = jsontolist.iterator();
			//while(it.hasNext()){
			while(i<6){
				BaseBean bb = (BaseBean) it.next();
				Article article = new Article();
				article.setTitle(bb.getName());
				article.setDescription(bb.getInfo());
				article.setPicUrl(bb.getIcon());
				article.setUrl(bb.getDetailurl());
				articleList.add(article);
				i++;
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsMessage);
			return respMessage;
		}else if(304000==json.getInt("code")){
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			int i = 1;
			List<Article> articleList = new ArrayList<Article>();
			List jsontolist = JsonUtil.JsonToListForTuling(json);
			Iterator it = jsontolist.iterator();
			while(i<6){
				SoftWareDownLoadBean swb = (SoftWareDownLoadBean)it.next();
				Article article = new Article();
				article.setTitle(swb.getName());
				article.setDescription("下载量："+swb.getCount());
				article.setPicUrl(swb.getIcon());
				article.setUrl(swb.getDetailurl());
				articleList.add(article);
				i++;
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsMessage);
			return respMessage;
		}else if(305000==json.getInt("code")){
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			int i = 1;
			List<Article> articleList = new ArrayList<Article>();
			List jsontolist = JsonUtil.JsonToListForTuling(json);
			Iterator it = jsontolist.iterator();
			while(i<5){
				TrainBean tb = (TrainBean)it.next();
				Article article = new Article();
				article.setTitle(tb.getTrainnum());
				String description = "出发地-目的地："+tb.getStart()+"-"+tb.getTerminal()+"\n"
				+"出发时间-到达时间："+tb.getStarttime()+"-"+tb.getEndtime();
				article.setDescription(description);
				article.setPicUrl(tb.getIcon());
				article.setUrl(tb.getDetailurl());
				articleList.add(article);
				i++;
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsMessage);
			return respMessage;
		}else if(306000==json.getInt("code")){
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			int i = 1;
			List<Article> articleList = new ArrayList<Article>();
			List jsontolist = JsonUtil.JsonToListForTuling(json);
			Iterator it = jsontolist.iterator();
			while(i<5){
				AirBean ab = (AirBean)it.next();
				Article article = new Article();
				article.setTitle(ab.getFlight());
				if(ab.getRoute()==null||ab.getRoute()==""){
					ab.setRoute("暂无");
				}
				if(ab.getState()==null||ab.getState()==""){
					ab.setState("暂无");
				}
				String description = "航班路线："+ab.getRoute()+"\n"
				+"起飞时间-到达时间："+ab.getStarttime()+"-"+ab.getEndtime()+"\n"
				+"航班状态："+ab.getState();
				article.setDescription(description);
				article.setPicUrl(ab.getIcon());
				article.setUrl(ab.getDetailurl());
				articleList.add(article);
				i++;
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsMessage);
			return respMessage;
			
		}else if(309000==json.getInt("code")){
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			int i = 1;
			List<Article> articleList = new ArrayList<Article>();
			List jsontolist = JsonUtil.JsonToListForTuling(json);
			Iterator it = jsontolist.iterator();
			while(i<5){
				HotelBean hb = (HotelBean)it.next();
				Article article = new Article();
				article.setTitle(hb.getName());
				if(hb.getCount()==null||hb.getCount()==""){
					hb.setCount("暂无");
				}/*
				if(ab.getState()==null||ab.getState()==""){
					ab.setState("暂无");
				}*/
				String description = "价格："+hb.getPrice()+"\n"
				+"满意度："+hb.getSatisfaction()+"\n"
				+"数量："+hb.getCount();
				article.setDescription(description);
				article.setPicUrl(hb.getIcon());
				article.setUrl(hb.getDetailurl());
				articleList.add(article);
				i++;
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsMessage);
			return respMessage;
		}else if(311000==json.getInt("code")){
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			int i = 1;
			List<Article> articleList = new ArrayList<Article>();
			List jsontolist = JsonUtil.JsonToListForTuling(json);
			Iterator it = jsontolist.iterator();
			while(i<5){
				PriceBean pb = (PriceBean)it.next();
				Article article = new Article();
				article.setTitle(pb.getName());
				/*if(hb.getCount()==null||hb.getCount()==""){
					hb.setCount("暂无");
				}
				if(ab.getState()==null||ab.getState()==""){
					ab.setState("暂无");
				}*/
				String description = "价格："+pb.getPrice()+"\n";
				article.setDescription(description);
				article.setPicUrl(pb.getIcon());
				article.setUrl(pb.getDetailurl());
				articleList.add(article);
				i++;
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsMessage);
			return respMessage;
		}else{
			respContent = "抱歉，跟你暂时无法沟通。";
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		}
		return respMessage;
		
	}
	
}
