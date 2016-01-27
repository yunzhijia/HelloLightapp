 package com.kingdee.lightapp.oauth;
 
 import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;
 
@SuppressWarnings("unused")
 public class OAuth implements Serializable
 {
private static final String HMAC_SHA1 = "HmacSHA1";
   private static final Parameter OAUTH_SIGNATURE_METHOD = new Parameter(
     "oauth_signature_method", "HMAC-SHA1");
   static final long serialVersionUID = -4368426677157998618L;
   private String consumerKey;
   private String consumerSecret;
   private String oauthVerifier;
  private static Random RAND = new Random();
 
   public OAuth(String consumerKey, String consumerSecret,String oauthVerifier)
   {
     setConsumerKey(consumerKey);
     setConsumerSecret(consumerSecret);
     if(StringUtils.isNotBlank(oauthVerifier)){
    	setOauthVerifier(oauthVerifier); 
     }
   }
 
   String generateAuthorizationHeader(String method, String url, Parameter[] params, String nonce, String timestamp, OAuthToken otoken)
   {
	   
     	if(null==params){
     		params=new Parameter[]{};
     	} 
	   
     	List<Parameter> oauthHeaderParams = new ArrayList<Parameter>(5);
     	oauthHeaderParams.add(new Parameter("oauth_consumer_key", this.consumerKey));
     	oauthHeaderParams.add(OAUTH_SIGNATURE_METHOD);
     	oauthHeaderParams.add(new Parameter("oauth_timestamp", timestamp));
     	oauthHeaderParams.add(new Parameter("oauth_nonce", nonce));
     	oauthHeaderParams.add(new Parameter("oauth_version", "1.0"));
     	if (otoken != null) {
     		oauthHeaderParams.add(new Parameter("oauth_token", otoken
     				.getToken()));
     	}
     	if(StringUtils.isNotBlank(this.oauthVerifier)){
     		oauthHeaderParams.add(new Parameter("oauth_verifier",this.oauthVerifier));
     	}
     	
     
     	List<Parameter>signatureBaseParams = new ArrayList<Parameter>(
        oauthHeaderParams.size() + params.length);
     	signatureBaseParams.addAll(oauthHeaderParams);
     	signatureBaseParams.addAll(toParamList(params));
     	parseGetParameters(url, signatureBaseParams);
 
     	StringBuffer base = new StringBuffer(method).append("&")
     			.append(encode(constructRequestURL(url))).append("&");
     			base.append(encode(normalizeRequestParameters(signatureBaseParams)));
 
        String oauthBaseString = base.toString();
        String signature = generateSignature(oauthBaseString, otoken);
 
        oauthHeaderParams.add(new Parameter("oauth_signature", signature));
        return "OAuth " + encodeParameters(oauthHeaderParams, ",", true);
   }
 
   private void parseGetParameters(String url, List<Parameter> signatureBaseParams)
   {
    int queryStart = url.indexOf("?");
    if (-1 != queryStart) {
      String[] queryStrs = url.substring(queryStart + 1).split("&");
       try {
       for (String query : queryStrs) {
          String[] split = query.split("=");
           if (split.length == 2)
            signatureBaseParams.add(new Parameter(
              URLDecoder.decode(split[0], "UTF-8"), URLDecoder.decode(
             split[1], "UTF-8")));
           else
           signatureBaseParams.add(new Parameter(
             URLDecoder.decode(split[0], "UTF-8"), ""));
         }
       }
       catch (UnsupportedEncodingException localUnsupportedEncodingException)
       {
       }
     }
   }
 
   public String generateAuthorizationHeader(String method, String url, Parameter[] params, OAuthToken token)
   {
     long timestamp = System.currentTimeMillis() / 1000L;
   long nonce = timestamp + RAND.nextInt();
    return generateAuthorizationHeader(method, url, params, 
      String.valueOf(nonce), String.valueOf(timestamp), token);
   }
 
   String generateSignature(String data, OAuthToken token)
   {
	   SecretKeySpec spec=null;
   byte[] byteHMAC = null;
     try {
       Mac mac = Mac.getInstance("HmacSHA1");
      if (token == null) {
        String oauthSignature = encode(this.consumerSecret) + "&";
        spec = new SecretKeySpec(oauthSignature.getBytes("UTF-8"), 
          "HmacSHA1");
       } else {
       if (token.getSecretKeySpec() == null) {
          String oauthSignature = encode(this.consumerSecret) + "&" + 
             encode(token.getTokenSecret());
          spec = new SecretKeySpec(oauthSignature.getBytes(), 
           "HmacSHA1");
           token.setSecretKeySpec(spec);
         }
        spec = token.getSecretKeySpec();
       }
      mac.init(spec);
       byteHMAC = mac.doFinal(data.getBytes("UTF-8"));
     } catch (InvalidKeyException e) {
     e.printStackTrace();
     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
     }
     catch (UnsupportedEncodingException e) {
      e.printStackTrace();
     }
    return new BASE64Encoder().encode(byteHMAC);
   }
 
   String generateSignature(String data) {
    return generateSignature(data, null);
   }
 
   public static String normalizeRequestParameters(Parameter[] params)
   {
   return normalizeRequestParameters(toParamList(params));
   }
 
   public static String normalizeRequestParameters(List<Parameter> params) {
   Collections.sort(params);
     return encodeParameters(params);
   }
 
   public static String normalizeAuthorizationHeaders(List<Parameter> params) {
   Collections.sort(params);
   return encodeParameters(params);
   }
 
   public static List<Parameter> toParamList(Parameter[] params) {
   List<Parameter> paramList = new ArrayList<Parameter>(params.length);
   paramList.addAll(Arrays.asList(params));
    return paramList;
   }
 
   public static String encodeParameters(List<Parameter> postParams)
   {
   return encodeParameters(postParams, "&", false);
   }
 
   public static String encodeParameters(List<Parameter> params, String splitter, boolean quot)
   {
   StringBuffer buf = new StringBuffer();
    for (Parameter param : params) {
     if (buf.length() != 0) {
        if (quot) {
         buf.append("\"");
         }
      buf.append(splitter);
       }
      buf.append(encode(param.getName())).append("=");
      if (quot) {
        buf.append("\"");
       }
      buf.append(encode(param.getValue()));
     }
    if ((buf.length() != 0) && 
      (quot)) {
      buf.append("\"");
     }
 
    return buf.toString();
   }
 
   public static String encode(String value)
   {
    String encoded = null;
     try {
      encoded = URLEncoder.encode(value, "UTF-8");
     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
     }
   StringBuffer buf = new StringBuffer(encoded.length());
 
     for (int i = 0; i < encoded.length(); i++) {
      char focus = encoded.charAt(i);
    if (focus == '*') {
       buf.append("%2A");
      } else if (focus == '+') {
    buf.append("%20");
      } else if ((focus == '%') && (i + 1 < encoded.length()) && 
        (encoded.charAt(i + 1) == '7') && 
        (encoded.charAt(i + 2) == 'E')) {
       buf.append('~');
        i += 2;
       } else {
       buf.append(focus);
       }
     }
  return buf.toString();
   }
 
   public static String constructRequestURL(String url)
   {
    int index = url.indexOf("?");
   if (-1 != index) {
      url = url.substring(0, index);
     }
  int slashIndex = url.indexOf("/", 8);
    String baseURL = url.substring(0, slashIndex).toLowerCase();
    int colonIndex = baseURL.indexOf(":", 8);
     if (-1 != colonIndex)
     {
     if ((baseURL.startsWith("http://")) && (baseURL.endsWith(":80")))
       {
        baseURL = baseURL.substring(0, colonIndex);
     } else if ((baseURL.startsWith("https://")) && 
         (baseURL.endsWith(":443")))
       {
      baseURL = baseURL.substring(0, colonIndex);
       }
     }
   url = baseURL + url.substring(slashIndex);
   return url;
   }
 
   
   
   
   public void setOauthVerifier(String oauthVerifier) {
	this.oauthVerifier = oauthVerifier;
}

public void setConsumerKey(String consumerKey) {
    this.consumerKey = (consumerKey != null ? consumerKey : "");
   }
 
   public void setConsumerSecret(String consumerSecret) {
    this.consumerSecret = (consumerSecret != null ? consumerSecret : "");
   }
 
   public boolean equals(Object o)
   {
    if (this == o)
       return true;
     if (!(o instanceof OAuth)) {
       return false;
     }
     OAuth oAuth = (OAuth)o;
 
    if (this.consumerKey != null ? !this.consumerKey.equals(oAuth.consumerKey) : 
      oAuth.consumerKey != null)
      return false;
    if (this.consumerSecret != null ? 
      !this.consumerSecret
      .equals(oAuth.consumerSecret) : oAuth.consumerSecret != null) {
       return false;
     }
    return true;
   }
 
   public int hashCode()
   {
    int result = this.consumerKey != null ? this.consumerKey.hashCode() : 0;
     result = 31 * result + (
       this.consumerSecret != null ? this.consumerSecret.hashCode() : 0);
    return result;
   }
 
   public String toString()
   {
    return "OAuth{consumerKey='" + this.consumerKey + '\'' + 
      ", consumerSecret='" + this.consumerSecret + '\'' + '}';
   }
 }
