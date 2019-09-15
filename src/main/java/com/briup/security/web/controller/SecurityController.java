package com.briup.security.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.briup.security.util.Message;
import com.briup.security.util.MessageUtil;


/**
 * @program: paz
 * @description: 发送请求，如果token为空，跳转到这个controller
 * @author: wangzh
 * @create: 2019-03-21 15:41
 */
@RestController
@RequestMapping("/authenticaion")
public class SecurityController {

	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	/**
	 * 当需要身份认证时，跳转到这里
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/login")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public Message<String> requireAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				// TODO 跳转到登陆页面
				redirectStrategy.sendRedirect(request, response, "/login.html");
			}
		}
		return MessageUtil.error(401,"访问的服务需要身份认证，请引导用户到登录页");
	}
}
