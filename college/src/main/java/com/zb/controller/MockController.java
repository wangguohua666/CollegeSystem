package com.zb.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mock")
@Slf4j
public class MockController {
	
	/**
	 * 模拟登录.
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, String url) {
		if (StringUtils.isNotEmpty(url)) {
			request.setAttribute("url", url);
		}
		request.getSession().setAttribute("user", "user");
		
		return "/loginsuccess";
	}
	
}
