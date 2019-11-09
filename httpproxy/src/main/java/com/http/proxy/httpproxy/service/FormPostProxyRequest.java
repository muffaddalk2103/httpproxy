/**
 * 
 */
package com.http.proxy.httpproxy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author muffa
 *
 */
@Component
public class FormPostProxyRequest implements ProxyRequest {
	private final static Logger LOGGER = LoggerFactory.getLogger(ProxyService.class); 
	@Autowired
	private ProxyService proxyService;
	/* (non-Javadoc)
	 * @see com.http.proxy.httpproxy.service.ProxyRequest#processRequest(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ResponseEntity<?> processRequest(HttpServletRequest httpServletRequest) throws IOException {
		LOGGER.info("inside processRequest of FormPostProxyRequest");
		HttpPost request = new HttpPost(proxyService.getRequestURL(httpServletRequest));
		proxyService.prepareHeader(httpServletRequest, request);
		List<NameValuePair> urlParameters = new ArrayList<>();
		Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String parameterName = parameterNames.nextElement();
			urlParameters.add(new BasicNameValuePair(parameterName, httpServletRequest.getParameter(parameterName)));	
		}
		request.setEntity(new UrlEncodedFormEntity(urlParameters));
		return proxyService.processRequest(request);
	}
	@Override
	public String getSupportedHttpMethod() {
		return "POST";
	}
	@Override
	public String getSupportedContentType() {
		return "application/x-www-form-urlencoded";
	}

}
