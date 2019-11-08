/**
 * 
 */
package com.http.proxy.httpproxy.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

/**
 * @author muffa
 *
 */
public class GetProxyRequest implements ProxyRequest {
	private final static Logger LOGGER = LoggerFactory.getLogger(ProxyService.class); 
	private HttpServletRequest httpServletRequest;
	public GetProxyRequest(HttpServletRequest request) {
		this.httpServletRequest=request;
	}
	/* (non-Javadoc)
	 * @see com.http.proxy.httpproxy.service.ProxyRequest#processRequest(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ResponseEntity<?> processRequest() throws IOException {
		LOGGER.info("inside processRequest of GetProxyRequest");
		HttpGet request = new HttpGet(ProxyService.getRequestURL(this.httpServletRequest));
		ProxyService.prepareHeader(this.httpServletRequest, request);
		return ProxyService.processRequest(request);
	}

}
