package com.kingdee.lightapp.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.EntityUtils;

import com.kingdee.lightapp.common.constants.HTTPConstants;



/**
 * 
 * httpClient4
 *
 */
public final class HttpClientHelper {
	/**日志*/
	protected static Log log = LogFactory.getLog(HttpClientHelper.class);
    private static HttpClientHelper instance = null;
    private static Lock lock = new ReentrantLock();
    private CloseableHttpClient httpClient;
    private PoolingHttpClientConnectionManager cm;
   
    private HttpClientHelper() {
    	 init();
    }
    
    private static HttpClient client = null;  
    static {  
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();  
        cm.setMaxTotal(128);  
        cm.setDefaultMaxPerRoute(128);  
        client = HttpClients.custom().setConnectionManager(cm).build();  
    }  

    public static HttpClientHelper getHttpClient() {
    	
        if (instance == null) {
            lock.lock();
            try{
            	if (instance == null) {
                    instance = new HttpClientHelper();
                }
            }finally{
            	 lock.unlock();
            }
        }
        return instance;
    }
    private void init() {
    	cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(HTTPConstants.HTTP_CLIENT_MAXTOTAL);
        cm.setDefaultMaxPerRoute(HTTPConstants.HTTP_CLIENT_MAXPERROTE);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        RequestConfig rc = RequestConfig.custom()
        .setConnectTimeout(HTTPConstants.HTTP_CLIENT_CONNECTIONTIMEOUT)// 设置连接时间
        .setSocketTimeout(HTTPConstants.HTTP_CLIENT_SOTIMEOUT)// 设置数据传输时间
        .build();

        httpClient = httpClientBuilder.setDefaultRequestConfig(rc).
                setConnectionManager(cm).build();
        
    }
   
    public byte[] executeAndReturnByte(HttpRequestBase request) {
        HttpEntity entity = null;
        CloseableHttpResponse resp = null;
        byte[] rtn = new byte[0];
        if (request == null) 
        	return rtn;
        try {
        	if(httpClient == null){
        		init();        		
        	}
        	
        	if(httpClient == null){
       		    log.error("\n{"+request.getURI().toString()+"}\nreturn error {httpClient获取异常！}");
        		return rtn;        		
        	}
        	resp = httpClient.execute(request);
        
            entity = resp.getEntity();
            int httpStatusCode=resp.getStatusLine().getStatusCode();
            
            if (httpStatusCode == 200) {
                String encoding = ("" + resp.getFirstHeader("Content-Encoding")).toLowerCase();
                if (encoding.indexOf("gzip") > 0) {
                    entity = new GzipDecompressingEntity(entity);
                }
                rtn = EntityUtils.toByteArray(entity);
            } else {
            	
            	//System.out.println(new String(EntityUtils.toByteArray(entity),"UTF-8"));
            	log.error(new String(EntityUtils.toByteArray(entity),"UTF-8"));
            	log.error("\n{"+request.getURI().toString()+"}\nreturn error {"+resp.getStatusLine().getStatusCode()+"}");
            }
        } catch (Exception e) {
         	log.error(e.getMessage(), e);
        } finally {
            EntityUtils.consumeQuietly(entity);
            if (resp != null) {
                try { resp.close();} catch (Exception ignore) {}
            }

			try {
				// 检查线程池状态，并动态调整（实验性）
				if (cm != null) {
					PoolStats poolStats = cm.getTotalStats();
					int pending = poolStats.getPending();
					if (pending > 10) {// 超过10个在等待状态，增加线程池大小
						log.error(poolStats);// 记录状态，其实应该是warn
						cm.setMaxTotal(Math.min(cm.getMaxTotal() + pending, HTTPConstants.HTTP_CLIENT_MAXTOTAL * 3));
						cm.setDefaultMaxPerRoute(Math.min(cm.getDefaultMaxPerRoute() + pending,
								HTTPConstants.HTTP_CLIENT_MAXPERROTE * 3));
					}
				}
			} catch (Exception ignore) {
			}
        }
        return rtn;
    }

    public String execute(HttpRequestBase request)  {
        byte[] bytes = executeAndReturnByte(request);
        if (bytes == null || bytes.length == 0) 
        	return null;
        try {
			return new String(bytes,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
    }
    
    
    /** 
     * 提交form表单 
     *  
     * @param url 
     * @param params 
     * @param connTimeout 
     * @param readTimeout 
     * @return 
     * @throws ConnectTimeoutException 
     * @throws SocketTimeoutException 
     * @throws Exception 
     */  
    public static String postForm(String url, Map<String, String> params,  
            Map<String, String> headers, Integer connTimeout,  
            Integer readTimeout) throws ConnectTimeoutException,  
            SocketTimeoutException, Exception {  
  
        HttpClient client = null;  
  
        HttpPost post = new HttpPost(url);  
        try {  
            if (params != null && !params.isEmpty()) {  
                List<NameValuePair> formParams = new ArrayList<org.apache.http.NameValuePair>();  
                Set<Entry<String, String>> entrySet = params.entrySet();  
                for (Entry<String, String> entry : entrySet) {  
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry  
                            .getValue()));  
                }  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(  
                        formParams, Consts.UTF_8);  
                post.setEntity(entity);  
            }  
            if (headers != null && !headers.isEmpty()) {  
                for (Entry<String, String> entry : headers.entrySet()) {  
                    post.addHeader(entry.getKey(), entry.getValue());  
                }  
            }  
            // 设置参数  
            Builder customReqConf = RequestConfig.custom();  
            if (connTimeout != null) {  
                customReqConf.setConnectTimeout(connTimeout);  
            }  
            if (readTimeout != null) {  
                customReqConf.setSocketTimeout(readTimeout);  
            }  
            post.setConfig(customReqConf.build());  
            HttpResponse res = null;  
            if (url.startsWith("https")) {  
                // 执行 Https 请求.  
                client = createSSLInsecureClient();  
                res = client.execute(post);  
            } else {  
                // 执行 Http 请求.  
                client = HttpClientHelper.client;  
                res = client.execute(post);  
            }  
            return IOUtils.toString(res.getEntity().getContent(), "UTF-8");  
        } finally {  
            post.releaseConnection();  
            if (url.startsWith("https") && client != null  
                    && client instanceof CloseableHttpClient) {  
                ((CloseableHttpClient) client).close();  
            }  
        }  
    }  
  
    private static CloseableHttpClient createSSLInsecureClient()  
            throws GeneralSecurityException {  
        try {  
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(  
                    null, new TrustStrategy() {  
                        public boolean isTrusted(X509Certificate[] chain,  
                                String authType) throws CertificateException {  
                            return true;  
                        }  
                    }).build();  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
                    sslContext, new X509HostnameVerifier() {  
  
                        @Override  
                        public boolean verify(String arg0, SSLSession arg1) {  
                            return true;  
                        }  
  
                        @Override  
                        public void verify(String host, SSLSocket ssl)  
                                throws IOException {  
                        }  
  
                        @Override  
                        public void verify(String host, X509Certificate cert)  
                                throws SSLException {  
                        }  
  
                        @Override  
                        public void verify(String host, String[] cns,  
                                String[] subjectAlts) throws SSLException {  
                        }  
  
                    });  
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();  
        } catch (GeneralSecurityException e) {  
            throw e;  
        }  
    }  
  
    

    /**
	 * 发送带JSON参数的http post请求
	 * 
	 * @param strUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sendPost(String strUrl, String params)
			throws Exception {
		StringBuffer result = new StringBuffer();

		try {
			sendHttp(result, strUrl, params);
		} catch (ConnectException e) {
			Thread.sleep(8000);
			sendHttp(result, strUrl, params);
		} catch (Exception e) {
			throw e;
		}

		return result.toString();
	}

	private static String sendHttp(StringBuffer result, String strUrl,
			String params) throws Exception {
		HttpURLConnection connection = null;
		URL url = new URL(strUrl);// 创建连接
		connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestMethod("POST"); // 设置请求方式
		connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
		connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式

		// 设置超时链接时间
		connection.setConnectTimeout(8000);

		connection.connect();

		OutputStreamWriter out = new OutputStreamWriter(
				connection.getOutputStream(), "UTF-8"); // utf-8编码
		out.append(params);
		out.flush();
		out.close();

		int HttpResult = connection.getResponseCode();

		if (HttpResult == HttpURLConnection.HTTP_OK) {
			// 读取响应
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));

			String line = null;
			while ((line = br.readLine()) != null) {
				result.append(line + "\n");
			}

			br.close();
		}else {
			result.append(HttpResult);
		}

		return result.toString();
	}
    
    
}
