package com.kingdee.lightapp.service.openorg.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.lightapp.common.constants.LightappURLConstants;
import com.kingdee.lightapp.oauth.OAuth;
import com.kingdee.lightapp.oauth.Parameter;
import com.kingdee.lightapp.service.LightappConfigureService;
import com.kingdee.lightapp.service.openorg.OpenorgService;
import com.kingdee.lightapp.utils.HttpClientHelper;
import com.kingdee.lightapp.utils.MapUtils;

@Service
public class OpenorgServiceImpl implements OpenorgService{
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenorgServiceImpl.class);
	
	@Autowired
	private LightappConfigureService lightAppConfigureService;
	
		private  String getOrgInfoBy(String consumerKey,
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
			  headers.put("User-Agent", userAgent);
			/*  headers.put("appId", consumerKey);*/
			  try {
				return HttpClientHelper.postForm(requestUrl, MapUtils.toHashMap(params), headers, 3000, 5000);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
			}
			return "";
			
		}
	    
	  

	  	@Override
		public  String getPersonInfoByEidAndOpenId(String userAgent,String openIds ,String eId){
			String consumerKey=lightAppConfigureService.getMcloudAppId();
			String consumerSecret=lightAppConfigureService.getMcloudAppSecret();
			String requestUrl=lightAppConfigureService.getKdweiboHost()+LightappURLConstants.OPENID_GET_PERSON;
			JSONObject params=new JSONObject();
			params.put("openIds", openIds);
			params.put("eid", eId);
		return getOrgInfoBy(consumerKey, consumerSecret, userAgent, params, requestUrl);
	  }
}
