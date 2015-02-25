package com.wickey.course.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import java.util.Iterator;  
import java.util.Set;  

import org.json.JSONException;
import org.json.JSONObject;

import com.wickey.course.bean.tulingbean.NewsBean;


public class TulingApiUtil {

	/**
	 * 调用图灵机器人api接口，获取智能回复内容后，解析获取自己所需的结果
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	
	public JSONObject getTulingResult(String content) throws UnsupportedEncodingException{
		String APIKEY = "dd9d8dcc959741cbba7acd9b3c6dfee5";
		String INFO = URLEncoder.encode(content,"utf-8");
		String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO; 
		
		
		String result = "";
		try {
			StringBuffer sb = new StringBuffer();
			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection)getUrl.openConnection();
			connection.connect();
			//取得输入流，并使用Reader读取
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			String line = "";
			while((line = reader.readLine())!=null){
				sb.append(line);
			}
			reader.close();
			connection.disconnect();
			
			JSONObject json = new JSONObject(sb.toString());
			/*if(100000==json.getInt("code")){
				result = json.getString("text");
			}else if(200000==json.getInt("code")){
				result = json.getString("text")+"\n"+json.getString("url");
			}else if(302000==json.getInt("code")){
				List jsontolist = JsonUtil.JsonToListForTuling(json);
				Iterator it = jsontolist.iterator();
				while(it.hasNext()){
					NewsBean nb = (NewsBean) it.next();
					System.out.println(nb.getArticle());
					System.out.println(nb.getDetailurl());
					System.out.println(nb.getSource());
					System.out.println(nb.getIcon());
				}
			}else{
				return null;
			}*/
			
			return json;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
}
