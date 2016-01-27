package com.kingdee.lightapp.common.constants;

public class HTTPConstants {

	/** HttpClient 读取超时(单位毫秒) sys.httpclient.sockettimeout */
	public static int HTTP_CLIENT_SOTIMEOUT = 120000;

	/** HttpClient 链接超时 (单位毫秒) sys.httpclient.connetciontimeout */
	public static int HTTP_CLIENT_CONNECTIONTIMEOUT = 120000;

	/** HttpClient 最大连接数 sys.httpclient.maxtotal */
	public static int HTTP_CLIENT_MAXTOTAL = 1024;

	/** HttpClient 最大连接数 maxperroute sys.httpclient.defaultmaxperroute */
	public static int HTTP_CLIENT_MAXPERROTE = 128;

}
