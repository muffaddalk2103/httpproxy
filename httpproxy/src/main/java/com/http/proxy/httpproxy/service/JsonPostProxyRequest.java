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
import org.springframework.http.ResponseEntity;

/**
 * @author muffa
 *
 */
public class JsonPostProxyRequest implements ProxyRequest {
	private final static Logger LOGGER = LoggerFactory.getLogger(ProxyService.class); 
	private HttpServletRequest httpServletRequest;
	public JsonPostProxyRequest(HttpServletRequest request) {
		this.httpServletRequest = request;
	}
	/* (non-Javadoc)
	 * @see com.http.proxy.httpproxy.service.ProxyRequest#processRequest(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ResponseEntity<?> processRequest() throws IOException {
		LOGGER.info("inside processRequest of JsonPostProxyRequest");
		HttpPost request = new HttpPost(ProxyService.getRequestURL(httpServletRequest));
		ProxyService.prepareHeader(httpServletRequest, request);
		request.setEntity(new StringEntity(IOUtils.toString(httpServletRequest.getReader())));
		return ProxyService.processRequest(request);
	}

}
