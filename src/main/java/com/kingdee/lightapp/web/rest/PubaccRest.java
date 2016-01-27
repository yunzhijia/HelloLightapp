package com.kingdee.lightapp.web.rest;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingdee.lightapp.authority.AuthorityType;
import com.kingdee.lightapp.authority.FireAuthority;
import com.kingdee.lightapp.authority.ResultTypeEnum;
import com.kingdee.lightapp.common.contexts.Context;
import com.kingdee.lightapp.domain.UserNetwork;
import com.kingdee.lightapp.service.pubacc.PubaccService;
import com.kingdee.lightapp.web.APIResponeJson;

@Controller
@RequestMapping(value = "/rest/pubacc")
public class PubaccRest extends BaseRest {

	
	@Autowired
	private PubaccService pubaccService;
	
	@FireAuthority(resultType = ResultTypeEnum.json, authorityTypes = AuthorityType.LAPP)
	@RequestMapping(value = { "/push/full/msg.json" }, method = RequestMethod.POST)
	@ResponseBody
	public String pushFullMsg(HttpServletRequest request, @RequestParam(defaultValue = "")  String title, @RequestParam(defaultValue = "")  String message)
			throws Exception {
		APIResponeJson apiResponeJson = APIResponeJson.getInstance();
		Context context = (Context) request.getSession()
				.getAttribute("CONTEXT");
		if (!StringUtils.isBlank(message) && context != null) {
			UserNetwork usernetwork=context.getUserNetwork();
			if(usernetwork!=null){
				String eId=usernetwork.getEid();
				title=usernetwork.getUsername()+"对你sayhi：";
				boolean bool=pubaccService.sendPubaccMsgByEid(eId, message,title);
				if(bool){
					apiResponeJson.setSuccess(true);
					apiResponeJson.setCode(200);
					apiResponeJson.setMsg("sendSuccess");
				}
			}
		}else{
			apiResponeJson.setSuccess(false);
			apiResponeJson.setCode(5000);
			apiResponeJson.setMsg("message不能为空");	
		}
		return JSONObject.fromObject(apiResponeJson).toString();
	}
	
	
	

}