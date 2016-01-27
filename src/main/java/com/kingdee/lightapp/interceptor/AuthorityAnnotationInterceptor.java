package com.kingdee.lightapp.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kingdee.lightapp.authority.AuthorityHelper;
import com.kingdee.lightapp.authority.AuthorityType;
import com.kingdee.lightapp.authority.FireAuthority;
import com.kingdee.lightapp.authority.ResultTypeEnum;
import com.kingdee.lightapp.common.contexts.Context;
import com.kingdee.lightapp.web.APIResponeJson;

public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private AuthorityHelper authorityHelper;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthorityAnnotationInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) {

		Context context =null;
		try {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			FireAuthority authPassport = handlerMethod
					.getMethodAnnotation(FireAuthority.class);
			context=(Context)request.getSession().getAttribute("CONTEXT");
			if (null == authPassport||(context!=null&&StringUtils.isNotBlank(context.getTicket()))) {
				return true;// 没有声明权限,放行
			}
			context= new Context();
			processHeaderInfo(request, response, context);
			boolean bool = false;
		
			
				for (AuthorityType at : authPassport.authorityTypes())
				{
					bool = authorityHelper.hasAuthority(at.getIndex(), context);
					if (bool)
					{
						break;
					}
				}
			if (!bool)
			{
				if (authPassport.resultType() == ResultTypeEnum.page)
				{// 采用传统页面进行提示
					StringBuilder sb = new StringBuilder();
					sb.append(request.getContextPath());
					sb.append("/views/userIndex.jsp?success=false&errorMsg=Unauthorized&code=401");
					response.sendRedirect(sb.toString());
				}
				else if (authPassport.resultType() == ResultTypeEnum.json)
				{// 采用ajax方式的进行提示
					response.setContentType("application/json;charset=UTF-8");
					PrintWriter writer = response.getWriter();
					APIResponeJson apiResponeJson=APIResponeJson.getInstance();
					apiResponeJson.setCode(401);
					apiResponeJson.setMsg("Unauthorized");
					writer.write(JSONObject.fromObject(apiResponeJson).toString());
					writer.flush();
				}
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("authorization failed :", e);
		}
		return true;
	}

	private void processHeaderInfo(HttpServletRequest request,
			HttpServletResponse response,Context context) {
		String ticket = StringUtils.trim(request.getParameter("ticket")); // 轻应用认证ticket
		context.setRequest(request);
		context.setResponse(response);
		context.setTicket(ticket);
	}

}