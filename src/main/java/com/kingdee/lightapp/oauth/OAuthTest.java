package com.kingdee.lightapp.oauth;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;

import com.kingdee.lightapp.utils.HttpClientHelper;
import com.kingdee.lightapp.utils.MapUtils;



public class OAuthTest {
	
	
		private static final String HOST="http://192.168.22.144"; 
		private static String personUrl =HOST + "/openapi/v1/post/opendata-control/data/getpersons.json"; //获取当前人员信息
	    private static String contextURL =HOST + "/openapi/v1/post/opendata-control/data/getancestororgs.json";//.获取当前部门的所有上级部门列表[管理员授权]
	    private static final Integer connTimeout=2000;
	    private static  final Integer readTimeout=5000;

	public static void main(String[] args) throws ConnectTimeoutException, SocketTimeoutException, Exception {
		
		String consumerKey="10001";
		String consumerSecret="kingdee10001";
		String userAgent="";
		JSONObject params=new JSONObject();
		params.put("openIds", "501a39b1-2282-11e4-9273-dc8e3044c615");
		params.put("eid", "10109");
		
		String resultStr=getOrgInfoBy(consumerKey, consumerSecret, userAgent, params,personUrl);
		if(resultStr!=null){
			System.out.println(resultStr);
			 JSONObject resultJson=JSONObject.fromObject(resultStr);
		 }
	}
	private static String getOrgInfoBy(String consumerKey,
			String consumerSecret, String userAgent, JSONObject params,String requestUrl){
		String METHON="POST";
		if(requestUrl.contains("/get/")){
			METHON="GET";
		}
		OAuth  oAuth = new OAuth(consumerKey, consumerSecret,"");
		Parameter[] requestParams= Parameter.convertJSON2ParameterArray(params);
		String authorization =oAuth.generateAuthorizationHeader(METHON, requestUrl,requestParams, null);
		Map<String, String>  headers=new HashMap<String, String>();
		  if (StringUtils.isNotBlank(authorization)) {
			  headers.put("Authorization", authorization);
	        }
		  headers.put("appId", consumerKey);
		  headers.put("User-Agent", userAgent);
		  try {
			return HttpClientHelper.postForm(requestUrl, MapUtils.toHashMap(params), headers, connTimeout, readTimeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}



	 
}
