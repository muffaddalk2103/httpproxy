/**
 * 
 */
package com.http.proxy.httpproxy.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.HttpGet;
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
public class GetProxyRequest implements ProxyRequest {
	private final static Logger LOGGER = LoggerFactory.getLogger(ProxyService.class); 
	@Autowired
	private ProxyService proxyService;
	/* (non-Javadoc)
	 * @see com.http.proxy.httpproxy.service.ProxyRequest#processRequest(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ResponseEntity<?> processRequest(HttpServletRequest httpServletRequest) throws IOException {
		LOGGER.info("inside processRequest of GetProxyRequest");
		HttpGet request = new HttpGet(proxyService.getRequestURL(httpServletRequest));
		proxyService.prepareHeader(httpServletRequest, request);
		return proxyService.processRequest(request);
	}
	@Override
	public String getSupportedHttpMethod() {
		return "GET";
	}
	@Override
	public String getSupportedContentType() {
		return "ALL";
	}

}
