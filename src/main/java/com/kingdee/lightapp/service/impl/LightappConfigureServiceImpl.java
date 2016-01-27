package com.kingdee.lightapp.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kingdee.lightapp.service.LightappConfigureService;

@Service
public class LightappConfigureServiceImpl implements LightappConfigureService {

	// 基础配置
	@Value("${localhost}")
	private String localhost;

	@Value("${kdweibo.host}")
	private String kdweibo_host;
	
	@Value("${mcloud.host}")
	private String mcloud_host;
	
	@Value("${pubacc.id}")
	private String pubacc_id;

	@Value("${pubacc.key}")
	private String pubacc_key;

	@Value("${lightapp.id}")
	private String lightapp_id;

	@Value("${lightapp.secret}")
	private String lightapp_secret;

	@Override
	public String getPubaccId() {
		return this.pubacc_id;
	}

	@Override
	public String getPubaccKey() {
		return this.pubacc_key;
	}

	@Override
	public String getMcloudAppId() {
		return this.lightapp_id;
	}

	@Override
	public String getMcloudAppSecret() {
		return this.lightapp_secret;
	}


	@Override
	public String getKdweiboHost() {
		return "http://"+this.kdweibo_host;
	}


	@Override
	public String getLocalhost() {
		// TODO Auto-generated method stub
		return "http://"+this.localhost;
	}

	@Override
	public String getMcloudHost() {
		// TODO Auto-generated method stub
		
		return "http://"+this.mcloud_host;
	}


}
