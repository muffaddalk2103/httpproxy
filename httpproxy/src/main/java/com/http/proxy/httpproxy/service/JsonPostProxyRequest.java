/**
 * 
 */
package com.http.proxy.httpproxy.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
public class JsonPostProxyRequest implements ProxyRequest {
	private final static Logger LOGGER = LoggerFactory.getLogger(JsonPostProxyRequest.class); 
	@Autowired
	private ProxyService proxyService;
	/* (non-Javadoc)
	 * @see com.http.proxy.httpproxy.service.ProxyRequest#processRequest(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ResponseEntity<?> processRequest(HttpServletRequest httpServletRequest) throws IOException {
		LOGGER.info("inside processRequest of JsonPostProxyRequest");
		HttpPost request = new HttpPost(proxyService.getRequestURL(httpServletRequest));
		proxyService.prepareHeader(httpServletRequest, request);
		request.setEntity(new StringEntity(IOUtils.toString(httpServletRequest.getReader())));
		return proxyService.processRequest(request);
	}
	@Override
	public String getSupportedHttpMethod() {
		return "POST";
	}
	@Override
	public String getSupportedContentType() {
		return "application/json";
	}

}
