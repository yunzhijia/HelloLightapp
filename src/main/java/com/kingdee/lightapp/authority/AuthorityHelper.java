package com.kingdee.lightapp.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingdee.lightapp.common.contexts.Context;

@Component
public class AuthorityHelper {

	@Autowired
	private TicketAuth ticketAuth;

	/**
	 * 说明:判断用户权限
	 * 
	 * @param akey
	 *            索引
	 * @param authQry
	 * @return
	 */
	public boolean hasAuthority(int akey, Context context) {
		if (context != null) {
				if(akey==0){
					 return  ticketAuth.verifyByTicket(context);
				}
			}
		return false;
	}

}
