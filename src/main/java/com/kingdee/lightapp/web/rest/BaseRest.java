package com.kingdee.lightapp.web.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kingdee.lightapp.web.APIResponeJson;

/**
 * 说明：
 */
public class BaseRest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseRest.class);

	@ExceptionHandler
	public String exception(HttpServletRequest request,
			HttpServletResponse response, Exception e) {
		try {
			PrintWriter writer = response.getWriter();
			writer.write(JSONObject.fromObject(APIResponeJson.getInstance()).toString());
			writer.flush();
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e1) {
			LOGGER.error(e1.getMessage(), e1);
		}
		return null;
	}
	
	
}
