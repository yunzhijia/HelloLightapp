package com.kingdee.lightapp.domain.pubacc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @since 公共号消息实体类   
 * @author kingdee
 *
 */
public class PubaccMsg {
	private String eid; //发送方企业的eid
	private String pubaccId;  // 公共号Id
	private String pubaccSecret;  // 公共号secret
	private String pushUrl;  //公共号请求地址
	
	private List<String> toUserOids;  // 接收方的userOid
	
	/**
	 * type=2:纯文本信息，需要设置text
	 * type=5:文本链接信，需要设置text appId url todo
	 * type=6:图文混排信息，需要设置appId todo model details
	 */
	private int type; 
	private String code;
	private String text;  //type为2时，发送文本信息
	private String appId;  //轻应用appId 可以为空
	private String url;  //文本链接地址，格式为经过URLENCODE编码的字符串
	private int todo;  //是否推送待办消息,格式为整型,默认1=推送到待办消息;0=推送原公共号消息
	private int model; // 1:单条文本编排模板     2:单条图文混排模板     3:多条图文混排模板      4:应用消息模板
	private List<PubaccMsgDetail> details;  //不同类型的model将推送不同的消息体
	
	public PubaccMsg(String eid,String pubaccId,String pubaccSecret,String pushUrl){
		this.eid = eid;
		this.pubaccId = pubaccId;
		this.pubaccSecret = pubaccSecret;
		this.pushUrl = pushUrl;
	}
	
	
	
	public PubaccMsg(){
	}
	
	
	
	
	
	
	public String getCode() {
		if(StringUtils.isBlank(code)){
			code="0";
		}
		return code;
	}



	public void setCode(String code) {
		
		this.code = code;
	}



	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public List<String> getToUserOids() {
		return toUserOids;
	}
	public void setToUserOids(List<String> toUsersOid) {
		this.toUserOids = toUsersOid;
	}
	public void addToUserOids(String userOid){
		if(toUserOids == null){
			toUserOids = new ArrayList<String>();
		}
		toUserOids.add(userOid);
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPubaccId() {
		return pubaccId;
	}
	public void setPubaccId(String pubaccId) {
		this.pubaccId = pubaccId;
	}
	public String getPubaccSecret() {
		return pubaccSecret;
	}
	public void setPubaccSecret(String pubaccSecret) {
		this.pubaccSecret = pubaccSecret;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public int getTodo() {
		return todo;
	}
	public void setTodo(int todo) {
		this.todo = todo;
	}
	public String getPushUrl() {
		return pushUrl;
	}
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	public List<PubaccMsgDetail> getDetails() {
		return details;
	}
	public void setDetails(List<PubaccMsgDetail> details) {
		this.details = details;
	}
	public void addDetail(PubaccMsgDetail detail){
		if(details == null){
			details = new ArrayList<PubaccMsgDetail>();
		}
		details.add(detail);
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
