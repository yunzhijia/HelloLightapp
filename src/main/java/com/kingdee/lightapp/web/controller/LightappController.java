package com.kingdee.lightapp.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kingdee.lightapp.authority.AuthorityType;
import com.kingdee.lightapp.authority.FireAuthority;
import com.kingdee.lightapp.authority.ResultTypeEnum;
import com.kingdee.lightapp.web.rest.BaseRest;

@Controller
public class LightappController extends BaseRest{

	
	@FireAuthority(resultType = ResultTypeEnum.page, authorityTypes = AuthorityType.LAPP)
	@RequestMapping(value = { "","/","/sayhi-list.json" })
	public String sayHiList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		return "list";
	}
	
	@FireAuthority(resultType = ResultTypeEnum.page, authorityTypes = AuthorityType.LAPP)
	@RequestMapping(value = { "/sayhi-create.json" })
	public String sayHiCreate(HttpServletRequest request,HttpServletResponse response) throws IOException {
			
		return "create";
	}
	
	@FireAuthority(resultType = ResultTypeEnum.page, authorityTypes = AuthorityType.LAPP)
	@RequestMapping(value = { "/sayhi-detail.json" })
	public String sayHiDetail(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		return "detail";
	}

}