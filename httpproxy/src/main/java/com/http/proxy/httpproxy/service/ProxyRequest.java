/**
 * 
 */
package com.http.proxy.httpproxy.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

/**
 * @author muffa
 *
 */
public interface ProxyRequest {
	ResponseEntity<?> processRequest(HttpServletRequest httpServletRequest) throws Exception;
	String getSupportedHttpMethod();
	String getSupportedContentType();
}
