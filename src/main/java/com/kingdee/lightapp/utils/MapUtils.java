package com.kingdee.lightapp.utils;

import java.util.HashMap;
import java.util.Iterator;

import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
public class MapUtils {

	public static HashMap<String, String> toHashMap(JSONObject jsonObject)  
	   {  
	       HashMap<String, String> data = new HashMap<String, String>();  
	       Iterator it = jsonObject.keys();  
	       // 遍历jsonObject数据，添加到Map对象  
	       while (it.hasNext())  
	       {  
	           String key = String.valueOf(it.next());  
	           String value = (String) jsonObject.get(key);  
	           data.put(key, value);  
	       }  
	       return data;  
	   }  
	
}
