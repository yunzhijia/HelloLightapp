package com.kingdee.lightapp.service.pubacc;

import java.util.List;

public interface PubaccService {
	
	
	boolean sendPubaccMsgByOpenIds(List<String> openIds,String eid,
		String message, String title)throws Exception;
	

	boolean sendPubaccMsgByEid(String eid, String message, String title)
			throws Exception;


	 

}
