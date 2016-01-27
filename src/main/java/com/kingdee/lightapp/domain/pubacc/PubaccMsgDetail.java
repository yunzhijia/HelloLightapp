package com.kingdee.lightapp.domain.pubacc;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 发送公共号中list的内容，抽象出来 2015-7-15
 * @author wang_kun
 */
public class PubaccMsgDetail {
	/**
	 * model=1:单文本消息（需要设置title text url） model=2:单图文消息（需要设置title text url
	 * picName picStream） model=3:多图文消息（多个单图文） model=4:应用消息（需要设置title text
	 * buttons）
	 */
	private String title; // 消息标题
	private String text; // 消息摘要
	private String url; // 原文链接,参数值需要URLENCODE编码
	private String picName; // 图片的名字
	private String picStream; // 图片的二进制字节流，格式为经过BASE64编码的字符串
	private List<PubaccMsgButton> buttons; // model为4的应用消息模板，可以添加响应按钮

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicStream() {
		return picStream;
	}

	public void setPicStream(String picStream) {
		this.picStream = picStream;
	}

	public List<PubaccMsgButton> getButtons() {
		return buttons;
	}

	public void setButtons(List<PubaccMsgButton> buttons) {
		this.buttons = buttons;
	}

	public void addButton(PubaccMsgButton button) {
		if (buttons == null) {
			buttons = new ArrayList<PubaccMsgButton>();
		}
		buttons.add(button);
	}
}
