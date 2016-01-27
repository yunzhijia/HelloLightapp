package com.kingdee.lightapp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class HttpHelper {
	/** 日志 */
	protected static Logger log = LoggerFactory.getLogger(HttpHelper.class);
	private static String UTF8 = "UTF-8";

	public static String post(Map<String, String> params, String url)
			throws Exception
	{

		HttpPost post = null;
		try
		{
			post = new HttpPost(url);
			List<NameValuePair> uvp = new LinkedList<NameValuePair>();

			Set<String> set = params.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext())
			{
				String key = it.next();
				uvp.add(new BasicNameValuePair(key, params.get(key)));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(uvp, UTF8);
			post.setEntity(entity);
			return HttpClientHelper.getHttpClient().execute(post);

		}
		finally
		{
			if (post != null)
			{
				post.abort();
			}
		}
	}

	public static String post(Map<String, String> headers,
			Map<String, String> params, String url) throws Exception
	{

		HttpPost post = null;
		try
		{
			post = new HttpPost(url);

			if (headers != null)
			{

				Set<String> set = headers.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext())
				{
					String key = it.next();
					post.addHeader(key, headers.get(key));
				}
			}

			if (params != null)
			{

				List<NameValuePair> uvp = new LinkedList<NameValuePair>();
				Set<String> set = params.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext())
				{
					String key = it.next();
					uvp.add(new BasicNameValuePair(key, params.get(key)));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(uvp,
						UTF8);
				post.setEntity(entity);
			}
			return HttpClientHelper.getHttpClient().execute(post);

		}
		finally
		{
			if (post != null)
			{
				post.abort();
			}
		}
	}

	public static byte[] getAsBytes(Map<String, String> headers,
			Map<String, String> params, String url)
	{

		HttpGet get = null;
		try
		{
			if (params != null)
			{
				StringBuffer uri = new StringBuffer(url);
				uri.append("?");

				Set<String> set = params.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext())
				{

					String key = it.next();
					String value = params.get(key);
					uri.append(urlUTF8Encode(key)).append("=")
							.append(urlUTF8Encode(value)).append("&");
				}
				uri.deleteCharAt(uri.length() - 1);
				get = new HttpGet(uri.toString());
			}
			else
			{
				get = new HttpGet(url);
			}

			if (headers != null)
			{

				Set<String> set = headers.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext())
				{
					String key = it.next();
					get.addHeader(key, headers.get(key));
				}
			}
			return HttpClientHelper.getHttpClient().executeAndReturnByte(get);
		}
		finally
		{
			if (get != null)
			{
				get.abort();
			}
		}
	}

	public static String get(Map<String, String> params, String url)
			throws Exception
	{

		HttpGet get = null;

		try
		{

			if (params != null)
			{
				StringBuffer uri = new StringBuffer(url);
				uri.append("?");

				Set<String> set = params.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext())
				{

					String key = it.next();
					String value = params.get(key);
					uri.append(urlUTF8Encode(key)).append("=")
							.append(urlUTF8Encode(value)).append("&");
				}
				uri.deleteCharAt(uri.length() - 1);
				get = new HttpGet(uri.toString());
			}
			else
			{
				get = new HttpGet(url);
			}
			return HttpClientHelper.getHttpClient().execute(get);
		}
		finally
		{
			if (get != null)
			{
				get.abort();
			}
		}
	}

	public static String post(Map<String, String> headers, String jsonObject,
			String url) throws Exception
	{

		HttpPost post = null;
		try
		{
			post = new HttpPost(url);

			if (headers != null)
			{

				Set<String> set = headers.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext())
				{
					String key = it.next();
					post.addHeader(key, headers.get(key));
				}
			}

			if (StringUtils.isEmpty(jsonObject))
			{

				throw new Exception("json参数为空！");
			}
			StringEntity entity = new StringEntity(jsonObject, "UTF-8");
			post.setEntity(entity);
			return HttpClientHelper.getHttpClient().execute(post);

		}
		finally
		{
			if (post != null)
			{
				post.abort();
			}
		}
	}

	public static String get(Map<String, String> headers,
			Map<String, String> params, String url) throws Exception
	{

		HttpGet get = null;

		try
		{

			if (params != null)
			{
				StringBuffer uri = new StringBuffer(url);
				uri.append("?");

				Set<String> set = params.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext())
				{

					String key = it.next();
					String value = params.get(key);
					uri.append(urlUTF8Encode(key)).append("=")
							.append(urlUTF8Encode(value)).append("&");
				}
				uri.deleteCharAt(uri.length() - 1);
				get = new HttpGet(uri.toString());
			}
			else
			{
				get = new HttpGet(url);
			}

			if (headers != null)
			{

				Set<String> set = headers.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext())
				{
					String key = it.next();
					get.addHeader(key, headers.get(key));
				}
			}

			return HttpClientHelper.getHttpClient().execute(get);
		}
		finally
		{
			if (get != null)
			{
				get.abort();
			}
		}
	}

	public static String get(Map<String, String> params, String url,
			String charset) throws Exception
	{

		HttpGet get = null;

		try
		{

			if (params != null)
			{
				StringBuffer uri = new StringBuffer(url);
				uri.append("?");

				Set<String> set = params.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext())
				{

					String key = it.next();
					String value = params.get(key);
					uri.append(urlEncode(key, charset)).append("=")
							.append(urlEncode(value, charset)).append("&");
				}
				uri.deleteCharAt(uri.length() - 1);
				get = new HttpGet(uri.toString());
			}
			else
			{
				get = new HttpGet(url);
			}
			return HttpClientHelper.getHttpClient().execute(get);
		}
		finally
		{
			if (get != null)
			{
				get.abort();
			}
		}
	}

	public static String post(Map<String, String> basicParams, String url,
			Map<String, byte[]> fileParams) throws Exception
	{

		HttpPost post = null;
		try
		{
			post = new HttpPost(url);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			Set<String> set1 = basicParams.keySet();
			Iterator<String> it = set1.iterator();
			while (it.hasNext())
			{
				String key = it.next();
				builder.addTextBody(key, basicParams.get(key));
			}

			Set<String> set2 = fileParams.keySet();
			Iterator<String> fit = set2.iterator();
			while (fit.hasNext())
			{
				String key = fit.next();
				builder.addBinaryBody(key, fileParams.get(key));
			}
			HttpEntity entity = builder.build();
			post.setEntity(entity);
			return HttpClientHelper.getHttpClient().execute(post);

		}
		finally
		{
			if (post != null)
			{
				post.abort();
			}
		}
	}

	public static String getClientIp(HttpServletRequest req)
	{

		String ip = req.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{

			ip = req.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{

			ip = req.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{

			ip = req.getRemoteAddr();
		}

		if (ip.length() < 5)
		{

			ip = "0.0.0.0";
		}
		return ip;
	}

	private static String urlUTF8Encode(String str)
	{
		try
		{
			return URLEncoder.encode(str, UTF8);
		}
		catch (Exception e)
		{
			log.error("error", e);
			return str;
		}
	}

	private static String urlEncode(String str, String charset)
			throws UnsupportedEncodingException
	{

		return URLEncoder.encode(str, charset);
	}
}