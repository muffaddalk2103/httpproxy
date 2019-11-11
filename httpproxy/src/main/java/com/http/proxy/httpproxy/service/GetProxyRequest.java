/**
 * 
 */
package com.http.proxy.httpproxy.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
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
	private final static Logger LOGGER = LoggerFactory.getLogger(GetProxyRequest.class); 
	@Autowired
	private ProxyService proxyService;
	/* (non-Javadoc)
	 * @see com.http.proxy.httpproxy.service.ProxyRequest#processRequest(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public ResponseEntity<?> processRequest(HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.info("inside processRequest of GetProxyRequest");
		HttpGet request = new HttpGet(proxyService.getRequestURL(httpServletRequest));
		proxyService.prepareHeader(httpServletRequest, request);
		Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
		if(parameterNames.hasMoreElements()){
			URIBuilder uri = new URIBuilder(request.getURI());
			while(parameterNames.hasMoreElements()){
				String parameterName = parameterNames.nextElement();
				uri.addParameter(parameterName, httpServletRequest.getParameter(parameterName));	
			}
			request.setURI(uri.build());
		}
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
