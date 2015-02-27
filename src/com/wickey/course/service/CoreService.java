package com.wickey.course.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.wickey.course.util.WeixinUtil;

/**
 * 核心服务类
 * @author fatboyliang
 * @date 2015-01-30
 */

public class CoreService {
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	private static Logger logger = Logger.getLogger(CoreService.class);
	
	
	public static String processRequest(String request){
		String respMessage = null;
		try {
			//默认返回的文本消息内容
			String respContent = "请求处理异常，请稍后尝试！";
			List respContent1 = null;
			//xml请求解析
			Map<String,String> requestMap = MessageUtil.parseXml(request);
			logger.info("收到微信服务器的发来信息：\n"+requestMap.toString());
			//发送方账号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			//公众账号
			String toUserName = requestMap.get("ToUserName");
			//消息类型
			String msgType = requestMap.get("MsgType");
			//消息内容
			String content = requestMap.get("Content");
			
			//回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			textMessage.setAgentID(ParaAPI.agentId);
			
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				
				if(content.equals("?")||content.equals("？")){
					String resp1 = "1.回复如：“打开百度”，获取网站链接。\n";
					String resp2 = "2.回复如：“我要看娱乐新闻”，得知最新新闻内容。\n";
					String resp3 = "3.回复如：“北京到上海的火车（或航班）”，得知最新消息。\n";
					String resp4 = "4.回复如：“北京中关村附近的酒店”，得到酒店消息。\n";
					String resp5 = "5.回复电影、视频、小说、菜价得到不同类别信息。\n";
					String resp6 = "6.最后还可以和我吹吹水。。。24小时奉陪！"+WeixinUtil.emoji(0x1F609);
					respContent = resp1+resp2+resp3+resp4+resp5+resp6;
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
					return respMessage;
				}
				
				
				JSONObject json = new TulingApiUtil().getTulingResult(content);
				if(100000==json.getInt("code")){
					respContent = json.getString("text");
				}else if(200000==json.getInt("code")){
					respContent = json.getString("text")+"\n"+json.getString("url");
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
					while(i<11){
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
					while(i<11){
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
					while(i<11){
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
					while(i<11){
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
					respContent = "文本消息！如需帮助回复？";
				}
				if("".equals(respContent)){
					respContent = "文本消息！如需帮助回复？";
				}
				
				
				
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
				respContent = "发送的是图片信息，如需帮助回复？";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
				respContent = "发送的是地理位置信息，如需帮助回复？";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
				respContent = "发送的是链接信息，如需帮助回复？";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				respContent = "发送的是音频信息，如需帮助回复？";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
				//
				String eventType = requestMap.get("Event");
				
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					respContent = "谢谢你的关注!\n"+
							"在此你可以获取很多消息，详情可回复 ？";
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					 // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息 
				}else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)){
					// TODO 自定义菜单
					String eventKey = requestMap.get("EventKey");
					logger.info("被点击按钮的key："+eventKey);
					if(eventKey.equals("11")){
						respContent = "天气预报菜单按钮被点击！";
					}else if(eventKey.equals("12")){
						respContent = "公交查询菜单按钮";
					}else if(eventKey.equals("13")){
						respContent = "我在哪菜单按钮";
					}else if(eventKey.equals("21")){
						respContent = "我要点歌菜单按钮";
					}else if(eventKey.equals("31")){
						respContent = "个人测试，回复？有更多帮助";
					}
				}
			}
			
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return respMessage;
	}

}
