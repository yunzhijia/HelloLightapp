package com.kingdee.lightapp.web.rest;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingdee.lightapp.authority.AuthorityType;
import com.kingdee.lightapp.authority.FireAuthority;
import com.kingdee.lightapp.authority.ResultTypeEnum;
import com.kingdee.lightapp.common.contexts.Context;
import com.kingdee.lightapp.web.APIResponeJson;


@Controller
@RequestMapping(value = "/rest")
public class UserService {


	@FireAuthority(resultType = ResultTypeEnum.json, authorityTypes = AuthorityType.LAPP)
	@RequestMapping(value = { "/get/context.json" }, method = RequestMethod.POST)
	@ResponseBody
	public String getContext(HttpServletRequest request, @RequestParam(defaultValue = "")  String title, @RequestParam(defaultValue = "")  String message)
			throws Exception {
		APIResponeJson apiResponeJson = APIResponeJson.getInstance();
		Context context = (Context) request.getSession()
				.getAttribute("CONTEXT");
		if (context != null) {
			apiResponeJson.setData(context);
			apiResponeJson.setSuccess(true);
			apiResponeJson.setCode(200);
			apiResponeJson.setMsg("context");
		}else{
			apiResponeJson.setSuccess(false);
			apiResponeJson.setCode(5000);
			apiResponeJson.setMsg("message不能为空");	
		}
		return JSONObject.fromObject(apiResponeJson).toString();
	}
	
}
