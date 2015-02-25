package com.wickey.course.util;


import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.json.JSONObject;

import com.wickey.course.bean.tulingbean.AirBean;
import com.wickey.course.bean.tulingbean.BaseBean;
import com.wickey.course.bean.tulingbean.HotelBean;
import com.wickey.course.bean.tulingbean.NewsBean;
import com.wickey.course.bean.tulingbean.PriceBean;
import com.wickey.course.bean.tulingbean.SoftWareDownLoadBean;
import com.wickey.course.bean.tulingbean.TrainBean;

public class JsonUtil {
	
	public static List JsonToListForTuling(JSONObject json) throws Exception{
		String list = json.getString("list");
		if(302000==json.getInt("code")){
			JSONArray jsonArray = JSONArray.fromObject(list);
			List jsonList = (List) JSONArray.toCollection(jsonArray,NewsBean.class);
			return jsonList;
			/*Iterator it = jsonList.iterator();
			while(it.hasNext()){
				NewsBean nb = (NewsBean) it.next();
				System.out.println(nb.getArticle());
			}*/
		}else if(304000==json.getInt("code")){
			JSONArray jsonArray = JSONArray.fromObject(list);
			List jsonList = (List) JSONArray.toCollection(jsonArray,SoftWareDownLoadBean.class);
			return jsonList;
		}else if(305000==json.getInt("code")){
			JSONArray jsonArray = JSONArray.fromObject(list);
			List jsonList = (List) JSONArray.toCollection(jsonArray,TrainBean.class);
			return jsonList;
		}else if(306000==json.getInt("code")){
			JSONArray jsonArray = JSONArray.fromObject(list);
			List jsonList = (List) JSONArray.toCollection(jsonArray,AirBean.class);
			return jsonList;
		}else if(308000==json.getInt("code")){
			JSONArray jsonArray = JSONArray.fromObject(list);
			List jsonList = (List) JSONArray.toCollection(jsonArray,BaseBean.class);
			return jsonList;
		}else if(309000==json.getInt("code")){
			JSONArray jsonArray = JSONArray.fromObject(list);
			List jsonList = (List) JSONArray.toCollection(jsonArray,HotelBean.class);
			return jsonList;
		}else if(311000==json.getInt("code")){
			JSONArray jsonArray = JSONArray.fromObject(list);
			List jsonList = (List) JSONArray.toCollection(jsonArray,PriceBean.class);
			return jsonList;
		}else{
			return null;
		}
		
		
	}
}
