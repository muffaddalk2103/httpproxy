/**
 * 
 */
package com.http.proxy.httpproxy.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import com.http.proxy.httpproxy.service.ProxyRequest;
import com.http.proxy.httpproxy.service.ProxyRequestFactory;

/**
 * @author muffa
 *
 */
@Controller
public class ProxyController {
	private final static Logger LOGGER = LoggerFactory.getLogger(ProxyController.class); 
	
	@RequestMapping(value="/proxy/**")
	public ResponseEntity<?> handleRequest(HttpServletRequest request){
		LOGGER.info("inside handleRequest of ProxyController");
		String requestUri = request.getRequestURI();
		requestUri = requestUri.substring(7,requestUri.length());
		try {
			ProxyRequest proxyRequest = ProxyRequestFactory.getProxyRequest(request);
			return proxyRequest.processRequest();
		} catch (final HttpClientErrorException e) {
			LOGGER.error(e.getMessage(),e);
			return new ResponseEntity<>(e.getResponseBodyAsByteArray(), e.getResponseHeaders(), e.getStatusCode());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
			return new ResponseEntity<>("Unexpected error", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return new ResponseEntity<>("Unexpected error", HttpStatus.BAD_REQUEST);
		}
	}
}
