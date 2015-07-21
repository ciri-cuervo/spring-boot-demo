package com.springdemo.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.springdemo.util.WebUtils;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestHelperService {

	@Autowired
	private HttpServletRequest request;

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public String getBaseUrl()
	{
		return WebUtils.getBaseUrl(request);
	}

}
