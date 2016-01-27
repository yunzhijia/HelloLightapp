package com.kingdee.lightapp.authority;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingdee.lightapp.common.contexts.Context;
import com.kingdee.lightapp.common.contexts.SecurityContext;
import com.kingdee.lightapp.domain.UserNetwork;
import com.kingdee.lightapp.service.mcloud.McloudService;
import com.kingdee.lightapp.utils.JsonUtils;

@Component
public class TicketAuth {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TicketAuth.class);

	@Autowired
	private McloudService mcloudService;

	@Autowired
	private SecurityContext securityContext;

	public boolean verifyByTicket(Context context) {
		boolean success = false;
		String ticket = context.getTicket();
		if (StringUtils.isNotBlank(ticket)) {
			try {
		
					JSONObject person = mcloudService
							.getContextByTicket(ticket);
					if (person != null) {
						UserNetwork	userNetwork= JsonUtils.fromJson(person.toString(),
								UserNetwork.class);
						if(userNetwork!=null){
							context.setUserNetwork(userNetwork);
							securityContext.processContext(context);
							success=true;
						}
					}
			} catch (Exception e) {
				LOGGER.error("ticket verify failed ! ", e);
			}
		}
		return success;
	}

}
