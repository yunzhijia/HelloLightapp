package com.kingdee.lightapp.common.contexts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingdee.lightapp.service.LightappConfigureService;

@Component
public class SecurityContext{

	@Autowired
	private LightappConfigureService lightappConfigureService;
	
	public void processContext(Context context) {
		if (context != null) {
			HttpServletRequest request = context.getRequest();
			HttpSession session = request.getSession();
			String lappId= lightappConfigureService.getMcloudAppId();
			String lappSecret= lightappConfigureService.getMcloudAppSecret();
			String pubaccId= lightappConfigureService.getPubaccId();
			String pubaccKey=lightappConfigureService.getPubaccKey();
			context.setRequest(null);
			context.setResponse(null);
			context.setLappId(lappId);
			context.setLappSecret(lappSecret);
			context.setPubaccId(pubaccId);
			context.setPubaccKey(pubaccKey);
			session.setAttribute("CONTEXT", context);
		}
	}
}
