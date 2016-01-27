package com.kingdee.lightapp.service.pubacc.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.kingdee.lightapp.common.constants.LightappURLConstants;
import com.kingdee.lightapp.domain.pubacc.PubaccMsg;
import com.kingdee.lightapp.domain.pubacc.PubaccMsgDetail;
import com.kingdee.lightapp.service.LightappConfigureService;
import com.kingdee.lightapp.service.openorg.OpenorgService;
import com.kingdee.lightapp.service.pubacc.PubaccService;
import com.kingdee.lightapp.utils.PubaccMsgUtils;

@Service
public class PubaccServiceImpl implements PubaccService{
	/*private static final Logger LOGGER = LoggerFactory.getLogger(PubaccServiceImpl.class);*/
	
	@Autowired
	private LightappConfigureService lightAppConfigureService;

	@Autowired
	private OpenorgService openorgService;
	
	@Override
	@Async
    public boolean sendPubaccMsgByOpenIds(List<String> openIds,String eid,String message, String title) {
	 	String resutl="";
    	List<PubaccMsgDetail> details = new ArrayList<PubaccMsgDetail>();
		PubaccMsgDetail pubaccMsgDetail = new PubaccMsgDetail();

			String pushUrl = lightAppConfigureService.getMcloudHost()
					+ LightappURLConstants.PUBACC_PUBSEND;
			String appId=lightAppConfigureService.getMcloudAppId();
			String pubaccKey=lightAppConfigureService.getPubaccKey();
    		String pubaccId=lightAppConfigureService.getPubaccId();
			PubaccMsg pm = new PubaccMsg();
			pm.setUrl("");
			pm.setAppId(appId);
			pm.setEid(eid);
			pm.setPubaccId(pubaccId);// 公共号Id
			pm.setPubaccSecret(pubaccKey);// 公共号密钥
			pm.setToUserOids(openIds);
			pm.setModel(1);
			pm.setCode("0");
			pm.setText(message);
			pm.setPushUrl(pushUrl);
			pubaccMsgDetail.setTitle(title);
			pubaccMsgDetail.setText(message);
			details.add(pubaccMsgDetail);
			pm.setDetails(details);
			resutl=PubaccMsgUtils.pushPubaccMsg(pm);
	
		if(StringUtils.isNotBlank(resutl)&&resutl.contains("true")){
			return true;
		}
		return false;
	}

	
    @Override
    @Async
    public boolean sendPubaccMsgByEid(String eid,String message,String title) throws Exception {
    	String result="";
    	List<PubaccMsgDetail> details = new ArrayList<PubaccMsgDetail>();
		PubaccMsgDetail pubaccMsgDetail = new PubaccMsgDetail();

    	
    		String pubaccKey=lightAppConfigureService.getPubaccKey();
    		String pubaccId=lightAppConfigureService.getPubaccId();
	    	String pushUrl = lightAppConfigureService.getMcloudHost()
					+ LightappURLConstants.PUBACC_PUBSEND;
			String appId=lightAppConfigureService.getMcloudAppId();
			PubaccMsg pm = new PubaccMsg();
			pm.setUrl("");
			pm.setType(6);
		/*	pm.setAppId(appId);*/
			pm.setPubaccId(pubaccId);// 公共号Id
			pm.setPubaccSecret(pubaccKey);// 公共号密钥
			pm.setEid(eid);
			/*pm.setText(message);*/
			pm.setCode("all");
			pm.setModel(1);
			pm.setPushUrl(pushUrl);
			pubaccMsgDetail.setTitle(title);
			pubaccMsgDetail.setText(message);
			details.add(pubaccMsgDetail);
			pm.setDetails(details);
			result=PubaccMsgUtils.pushPubaccMsg(pm);
			if(StringUtils.isNotBlank(result)&&(result.contains("true")||result.contains("pubId"))){
				return true;
			}
			return false;
		
	}
    


    
}
