package com.kingdee.lightapp.domain.pubacc;

/**
 * @since model为4时是应用消息模板的响应按钮  2015-7-15
 * @author wang_kun
 */
public class PubaccMsgButton {
	private String title; //消息标题
	private String text;  //消息摘要
	/* event需要传入已下信息(URLENCODE utf-8编码)：
	     String eventViewId  该id跟button上的id一致
	     String subAppDesc  按钮对应事件的访问url地址
	     String url
	     JSONObject reqData  参数
	     String reqType  请求url方式 get/post
	     String contentType  传参类型
	     boolean autoReply  是否需要自动回复
	     String appId  轻应用Id
	 */
	private String event;
	private String url;
	private int id;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
