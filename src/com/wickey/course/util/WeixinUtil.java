package com.wickey.course.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;



import com.wickey.course.bean.pojo.AccessToken;
import com.wickey.course.bean.pojo.Menu;



/**
 * 
 * 公众平台公用接口工具类
 * @author fatboyliang
 * @date 2015-02-16
 */


public class WeixinUtil {
	private static Logger logger = Logger.getLogger(WeixinUtil.class);
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return （通过JSONObject.get(key)的方式获取json对象的属性值）
	 */
	
	
	public static JSONObject httpRequest(String requestUrl,String requestMethod,String outputStr){
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		
		try {
			//创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			//从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);
			//设置请求方式(GET/POST)
            httpUrlConn.setRequestMethod(requestMethod);
            
            if("GET".equalsIgnoreCase(requestMethod)){
            	httpUrlConn.connect();
            }
            
            //当有数据需要提交时
            if(null != outputStr){
            	OutputStream outputStream = httpUrlConn.getOutputStream();
            	//编码格式，防止中文乱码
            	outputStream.write(outputStr.getBytes("UTF-8"));
            	outputStream.close();
            }
            
            //将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            
            String str = null;
            while((str=bufferedReader.readLine())!=null){
            	buffer.append(str);
            }
            
            bufferedReader.close();
            inputStreamReader.close();
            //释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
			
		} catch (ConnectException e){
			logger.error("wechat server connection timed out");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("https request error:{}",e);
		}
		
		return jsonObject;
	}

	//获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url="https://api.weixin.qq.com/cgi-bin/token?"
			+ "grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	public final static String access_token_url_crop="https://qyapi.weixin.qq.com/cgi-bin/gettoken?"
			+ "corpid=id&corpsecret=secrect";
	
	
	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	
	public static AccessToken getAccessToken(String appid,String appsecret){
		
		System.out.println(appid);
		AccessToken accessToken = null;
		//String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		String requestUrl = access_token_url_crop.replaceAll("=id", "="+appid).replace("=secrect", "="+appsecret);
		System.out.println(requestUrl);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		System.out.println(jsonObject.toString());
		if(null != jsonObject){
			logger.info("收到的access_token："+jsonObject.getString("access_token")+"\n收到的expires_in："+jsonObject.getInt("expires_in"));
			//logger.info("收到的access_token："+jsonObject.getString("access_token"));
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				accessToken = null;
				logger.error("获取token失败，errcode:{"+jsonObject.getString("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
				
				// TODO: handle exception
			}
		}
		
		return accessToken;
		
	}
	
	
	//菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?"
			+ "access_token=ACCESS_TOKEN";
	public static String menu_create_url_crop = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?"
			+ "access_token=ACCESS_TOKEN&agentid=1";
	
	
	
	public static int createMenu(Menu menu,String accessToken){
		
		int result = 0;
		//拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		//将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		logger.info("jsonmenu字符串：\n"+JSONObject.fromObject(menu).toString());
		//调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
		
		if(null != jsonObject){
			if(0 != jsonObject.getInt("errcode")){
				result = jsonObject.getInt("errcode");
				logger.error("创建菜单失败，errcode:{"+jsonObject.getString("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
			        
			}
		}
		
		
		return result;
	}
	
	
public static int createMenuCrop(Menu menu,String accessToken,String agentId){
		
		int result = 0;
		//拼装创建菜单的url
		String url = menu_create_url_crop.replace("ACCESS_TOKEN", accessToken).replace("1", agentId);
		System.out.println(url);
		//将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		logger.info("jsonmenu字符串：\n"+JSONObject.fromObject(menu).toString());
		//调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
		
		if(null != jsonObject){
			if(0 != jsonObject.getInt("errcode")){
				result = jsonObject.getInt("errcode");
				logger.error("创建菜单失败，errcode:{"+jsonObject.getString("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
			        
			}
		}
		
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static String emoji(int hexEmoji) {  
	    return String.valueOf(Character.toChars(hexEmoji));  
	} 
	
	
}
