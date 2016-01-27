package com.kingdee.lightapp.service.mcloud.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingdee.lightapp.common.constants.LightappURLConstants;
import com.kingdee.lightapp.service.LightappConfigureService;
import com.kingdee.lightapp.service.mcloud.McloudService;
import com.kingdee.lightapp.utils.HttpHelper;
import com.kingdee.lightapp.utils.SHAUtils;

@Service
public class McloudServiceImpl implements McloudService{
	

	@Autowired
	private LightappConfigureService lightAppConfigureService;

	/**
	 * 说明：根据Ticket获取上下文
	 * 
	 * @param ticket
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject getContextByTicket(String ticket) throws Exception {
		String appId = lightAppConfigureService.getMcloudAppId();
		String appSecret = lightAppConfigureService.getMcloudAppSecret();
		String token = getAccessToken(appId, appSecret);
		String url = lightAppConfigureService.getMcloudHost()+LightappURLConstants.OPENAUTH2_GET_CONTEXT;
		Map<String, String> params = new HashMap<String,String>();
		params.put("access_token", token);
		params.put("ticket", ticket);
		String result = HttpHelper.get(params, url);
		return StringUtils.isBlank(result) ? null : (new JSONObject()
				.fromObject(result));

	}

	
	/**
	 * 说明：获取AccessToken
	 * 
	 * { "success": true, "data": { "expires_in": 604800, "access_token": "XXX"
	 * } }
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getAccessToken(String appId, String appSecret)
			throws Exception {
		String access_token ="";
		String version = "1.1";
		String nonce = String.valueOf(Math.random());
		String timestamp = String.valueOf(System.currentTimeMillis());
		String sign = SHAUtils.sha(version, appId, timestamp, nonce, appSecret);
		String url = lightAppConfigureService.getMcloudHost()+LightappURLConstants.OPENAUTH2_APP_AUTH2;
		StringBuffer  sb=new StringBuffer();
		sb.append("OpenAuth2 version=");
		sb.append(version);
		sb.append(", appid=");
		sb.append(appId);
		sb.append(", timestamp=");
		sb.append(timestamp);
		sb.append(", nonce=");
		sb.append(nonce);
		sb.append(", sign=");
		sb.append(sign);
		Map<String, String> headers =new HashMap<String,String>();
		headers.put("authorization", sb.toString());
		Map<String, String> params =null;
		String result = HttpHelper.get(headers,params, url);
		JSONObject resultJson = new JSONObject().fromObject(result);	
		if(resultJson!=null){
			JSONObject dataJson = resultJson.getJSONObject("data");
			if(dataJson!=null){
			 access_token = dataJson.getString("access_token");
			}
		}
		return access_token;
	}




}
