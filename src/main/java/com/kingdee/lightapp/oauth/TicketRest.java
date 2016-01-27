package com.kingdee.lightapp.oauth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.kingdee.lightapp.utils.HttpHelper;

public class TicketRest {

	
	private static final String OPENAUTH2_APP_AUTH2_URL="http://192.168.22.142/openauth2/api/appAuth2";
	public static final String OPENAUTH2_GET_CONTEXT_URL = "http://192.168.22.142/openauth2/api/getcontext";
	
	public static void main(String[] args) {
		try {
			String appId="10001";
			String appSecret ="kingdee10001";
			String ticket ="6620bacde73629e9f300450fa5a0b9b5";
			String context=getContextByTicket(ticket, appId, appSecret);
			System.out.println(context);
	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 说明：根据ticket获取上下文信息
	 * @param ticket 
	 * @param appId 
	 * @param appSecret  
	 * @return
	 * @throws Exception
	 */
	private static String getContextByTicket(String ticket, String appId,
			String appSecret) throws Exception {
		String token = getAccessToken(appId, appSecret);
		Map<String, String> params = new HashMap<String,String>();
		params.put("access_token", token);
		params.put("ticket", ticket);
		String result = HttpHelper.get(params, OPENAUTH2_GET_CONTEXT_URL);
		return result;

	}
	
	/**
	 * 说明：根据appId和appSecret获取access_token
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	private static String getAccessToken(String appId, String appSecret) throws Exception {

		String access_token = "";
		
		String version = "1.1";
		String nonce = String.valueOf(Math.random());
		String timestamp = String.valueOf(System.currentTimeMillis());
		String sign = sha(version, appId, timestamp, nonce, appSecret);
		// 注意：authorization参数需要放入请求头中
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
		String result = HttpHelper.get(headers,params, OPENAUTH2_APP_AUTH2_URL);
		
		JSONObject resultJ = new JSONObject().fromObject(result);	
		JSONObject dataJson = resultJ.getJSONObject("data");
		access_token = dataJson.getString("access_token");
		return access_token;
	}
	
	private static String sha(String... data)
	{
		//按字母顺序排序数组
		Arrays.sort(data);
		//把数组连接成字符串（无分隔符），并sha1哈希
        return DigestUtils.shaHex(StringUtils.join(data));

	}
	
	
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 * @throws Exception
	 */
	private static String sendGet(String url, String param) throws Exception {
		String result = "";
		BufferedReader in = null;
		URL realUrl = null;
		URLConnection connection = null;
		try {
			if (StringUtils.isNotBlank(param)
					&& param.contains("authorization=")) {
				String urlNameString = url;
				realUrl = new URL(urlNameString);
				// 打开和URL之间的连接
				connection = realUrl.openConnection();
				// 设置通用的请求属性
				String authorization = param.substring(
						"authorization=".length(), param.length());
				connection.setRequestProperty("authorization", authorization);
			} else {
				String urlNameString = url + "?" + param;
				realUrl = new URL(urlNameString);
				// 打开和URL之间的连接
				connection = realUrl.openConnection();
			}
			connection.setRequestProperty("ContentType",
					"text/xml;charset=utf-8");
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw e;
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
			}
		}
		return result;
	}
	
}
