/**
 * 
 */
package com.http.proxy.httpproxy.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

/**
 * @author muffa
 *
 */
public interface ProxyRequest {
	ResponseEntity<?> processRequest(HttpServletRequest httpServletRequest) throws IOException;
	String getSupportedHttpMethod();
	String getSupportedContentType();
}
