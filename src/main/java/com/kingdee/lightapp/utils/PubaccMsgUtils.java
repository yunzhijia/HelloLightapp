package com.kingdee.lightapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingdee.lightapp.domain.pubacc.PubaccMsg;
import com.kingdee.lightapp.domain.pubacc.PubaccMsgButton;
import com.kingdee.lightapp.domain.pubacc.PubaccMsgDetail;

/**
 * @since 公共号推送工具类 2015-7-15
 * @author kingdee
 */
public class PubaccMsgUtils {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PubaccMsgUtils.class);

	/**
	 * 支持type：6 model：1、 2、 3
	 * 
	 * @param pm
	 * @return
	 */
	public static String pushPubaccMsg(PubaccMsg pm) {
		String resultStr = "";
		JSONObject detail = null;
		try {
			String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.format(new Date());
			JSONObject from = genereteFrom(pm);
			JSONArray tos = new JSONArray();
			JSONObject to = new JSONObject();
			to.put("no", pm.getEid());
			to.put("user", pm.getToUserOids());
			to.put("code", pm.getCode());
		
			tos.add(to);
			List<PubaccMsgDetail> pmDetails = pm.getDetails();
			JSONArray details = new JSONArray();
			if (pmDetails != null && pmDetails.size() > 0) {
				for (PubaccMsgDetail pmDetail : pmDetails) {
					detail = new JSONObject();
					detail.put("title", pmDetail.getTitle());
					detail.put("text", pmDetail.getText());
					detail.put("url", pmDetail.getUrl());
					detail.put("appid", pm.getAppId());
					if (!StringUtils.isEmpty(pmDetail.getPicName())) {
						detail.put("name", pmDetail.getPicName());
					}
					if (!StringUtils.isEmpty(pmDetail.getPicStream())) {
						detail.put("pic", pmDetail.getPicStream());
					}
					detail.put("date", nowDate);
					details.add(detail);
				}
			}

			JSONObject msg = new JSONObject();
			msg.put("model", pm.getModel());
			msg.put("todo", pm.getTodo());
			msg.put("list", details);

			JSONObject content = new JSONObject();
			content.put("from", from);
			content.put("to", tos);
			content.put("type", pm.getType());
			content.put("msg", msg);
			resultStr = HttpClientHelper.sendPost(pm.getPushUrl(), content.toString());
			LOGGER.info("发送公共号消息：" + content.toString()+"调用公共号返回：" + resultStr);
		} catch (Exception e) {
			LOGGER.error("send pubacc msg failed" + e.getMessage(), e);
		}
		return resultStr;
	}

	public static String pushModel4Msg(PubaccMsg pm) {
		String resultStr = "";
		try {
			// String nowDate = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
			JSONObject from = genereteFrom(pm);
			JSONArray tos = new JSONArray();
			JSONObject to = new JSONObject();
			to.put("no", pm.getEid());
			to.put("user", pm.getToUserOids());
			tos.add(to);

			List<PubaccMsgDetail> pmDetails = pm.getDetails();
			JSONArray details = new JSONArray();
			for (int index = 0; index < pmDetails.size(); index++) {
				PubaccMsgDetail pmDetail = pmDetails.get(index);
				JSONObject detail = new JSONObject();
				detail.put("title", pmDetail.getTitle());
				detail.put("text", pmDetail.getText());
				// detail.put("date", nowDate);
				List<PubaccMsgButton> pmButtons = pmDetail.getButtons();
				JSONArray buttons = new JSONArray();
				for (PubaccMsgButton pmButton : pmButtons) {
					JSONObject button = new JSONObject();
					button.put("title", pmButton.getTitle());
					button.put("event", pmButton.getEvent());
					button.put("url", pmButton.getUrl());
					button.put("appid", pm.getAppId());
					buttons.add(button);
				}
				detail.put("button", buttons);
				details.add(detail);
			}

			JSONObject msg = new JSONObject();
			msg.put("model", pm.getModel());
			msg.put("todo", pm.getTodo());
			msg.put("list", details);

			JSONObject content = new JSONObject();
			content.put("from", from);
			content.put("to", tos);
			content.put("type", pm.getType());
			content.put("msg", msg);
			resultStr = HttpClientHelper.sendPost(pm.getPushUrl(), content.toString());
			LOGGER.info("pushModel4Msg()发送公共号消息：" + content.toString()+"pushModel4Msg()调用公共号返回：" + resultStr);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return resultStr;
	}

	public static String pushType2Msg(PubaccMsg pm) {
		String resultStr = "";
		try {
			JSONObject from = genereteFrom(pm);
			JSONArray tos = new JSONArray();
			JSONObject to = new JSONObject();
			to.put("no", pm.getEid());
			to.put("user", pm.getToUserOids());
			tos.add(to);

			JSONObject msg = new JSONObject();
			msg.put("text", pm.getText());

			JSONObject content = new JSONObject();
			content.put("from", from);
			content.put("to", tos);
			content.put("type", pm.getType());
			content.put("msg", msg);
			resultStr = HttpClientHelper.sendPost(pm.getPushUrl(), content.toString());
			LOGGER.info("pushType2Msg()发送公共号消息：" + content.toString()+"pushType2Msg()调用公共号返回：" + resultStr);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return resultStr;
	}

	public static String pushType5Msg(PubaccMsg pm) {
		String resultStr = "";
		try {
			JSONObject from = genereteFrom(pm);
			JSONArray tos = new JSONArray();
			JSONObject to = new JSONObject();
			to.put("no", pm.getEid());
			to.put("user", pm.getToUserOids());
			tos.add(to);

			JSONObject msg = new JSONObject();
			msg.put("text", pm.getText());
			msg.put("url", pm.getUrl());
			msg.put("appid", pm.getAppId());
			msg.put("todo", pm.getTodo());

			JSONObject content = new JSONObject();
			content.put("from", from);
			content.put("to", tos);
			content.put("type", pm.getType());
			content.put("msg", msg);
			resultStr = HttpClientHelper.sendPost(pm.getPushUrl(), content.toString());
			LOGGER.info("pushType5Msg()发送公共号消息：" + content.toString()+"pushType5Msg()调用公共号返回：" + resultStr);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return resultStr;
	}

	/**
	 * @since 生成公共号消息体的from对象 "no":"发送方企业的企业注册号(eID)，格式为字符串",
	 *        "pub":"发送使用的公共号ID，格式为字符串",
	 *        "time":"发送时间，为'currentTimeMillis()以毫秒为单位的当前时间'的字符串或数字",
	 *        "nonce":"随机数，格式为字符串或数字",
	 *        "pubtoken":=sha(no,pubaccId,pubaccSecret,nonce,time)
	 * @param pm
	 * @return
	 */
	private static JSONObject genereteFrom(PubaccMsg pm) {
		JSONObject from = new JSONObject();
		String nonce = String.valueOf(Math.random());
		long time = System.currentTimeMillis();
		from.put("no", pm.getEid());
		from.put("pub", pm.getPubaccId());
		from.put("time", time);
		from.put("nonce", nonce);
		from.put(
				"pubtoken",
				SHAUtils.sha(pm.getEid(), pm.getPubaccId(), pm.getPubaccSecret(), nonce,
						String.valueOf(time)));
		return from;
	}



	

}
