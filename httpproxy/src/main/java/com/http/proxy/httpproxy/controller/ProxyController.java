/**
 * 
 */
package com.http.proxy.httpproxy.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import com.http.proxy.httpproxy.service.ProxyService;

/**
 * @author muffa
 *
 */
@Controller
public class ProxyController {
	private final static Logger LOGGER = LoggerFactory.getLogger(ProxyController.class); 

	@Autowired
	private ProxyService proxyService;
	
	@RequestMapping(value="/proxy/**")
	public ResponseEntity<?> handleRequest(HttpServletRequest request){
		LOGGER.info("inside handleRequest of ProxyController");
		String requestUri = request.getRequestURI();
		requestUri = requestUri.substring(7,requestUri.length());
		try {
			switch(request.getMethod().toUpperCase()){
			case "POST":
				return proxyService.processPostRequest(requestUri, request);
			case "GET":
				return proxyService.processGetRequest(requestUri, request);
				default:
					return new ResponseEntity<>("Unsupported http method "+request.getMethod(),HttpStatus.BAD_REQUEST);
			}
		} catch (final HttpClientErrorException e) {
			LOGGER.error(e.getMessage(),e);
			return new ResponseEntity<>(e.getResponseBodyAsByteArray(), e.getResponseHeaders(), e.getStatusCode());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
			return new ResponseEntity<>("Unexpected error", HttpStatus.BAD_REQUEST);
		}
	}
}
