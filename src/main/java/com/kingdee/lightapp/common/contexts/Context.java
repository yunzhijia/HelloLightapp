package com.kingdee.lightapp.common.contexts;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingdee.lightapp.domain.UserNetwork;


public class Context implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5041632616100530845L;

	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String ticket;
	private String lappId;
	private String lappSecret;
	private String pubaccId;
	private String pubaccKey;
	
	
	private  UserNetwork userNetwork;
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	
	public String getLappId() {
		return lappId;
	}
	public void setLappId(String lappId) {
		this.lappId = lappId;
	}
	public String getLappSecret() {
		return lappSecret;
	}
	public void setLappSecret(String lappSecret) {
		this.lappSecret = lappSecret;
	}
	public String getPubaccId() {
		return pubaccId;
	}
	public void setPubaccId(String pubaccId) {
		this.pubaccId = pubaccId;
	}
	public String getPubaccKey() {
		return pubaccKey;
	}
	public void setPubaccKey(String pubaccKey) {
		this.pubaccKey = pubaccKey;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public UserNetwork getUserNetwork() {
		return userNetwork;
	}
	public void setUserNetwork(UserNetwork userNetwork) {
		this.userNetwork = userNetwork;
	}
	


}
