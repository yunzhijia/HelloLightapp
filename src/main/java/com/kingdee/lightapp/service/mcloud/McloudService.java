package com.kingdee.lightapp.service.mcloud;

import net.sf.json.JSONObject;


public interface McloudService {



	String getAccessToken(String appId, String appSecret) throws Exception;


	JSONObject getContextByTicket(String ticket) throws Exception;
}
