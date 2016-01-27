package com.kingdee.lightapp.web;

import org.apache.commons.lang3.StringUtils;


/**
 * 定义 API 返回的 Json 格式
 * 
 */
public  class APIResponeJson{
	

     static class SingletonHolder {  
        static  APIResponeJson instance = new APIResponeJson();
    }  
  
    public static APIResponeJson getInstance() {  
        return SingletonHolder.instance;  
    }  
  


	public APIResponeJson() {
	}


	private   boolean success;
	private  String msg; // 错误时的信息
	private  int code; // 错误时的状态码

	private  Object data; // 返回的 业务 json 数据

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		if(StringUtils.isBlank(msg)){
			msg="Internal Server Error";
		}
		return msg;
	}

	public int getCode() {
		if(code<=0){
			code=500;
		}
		return code;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}


	public static void main(String[] args)
	{
		String agent = "BULUO/0.9.0;Android4.1.1;Xiaomi;MI 2;deviceId：0000556565,";
		agent = agent.substring(0, agent.indexOf("/"));
		System.out.println(agent);
	}


}
