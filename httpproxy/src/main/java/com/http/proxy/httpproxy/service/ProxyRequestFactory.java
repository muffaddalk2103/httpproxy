/**
 * 
 */
package com.http.proxy.httpproxy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author muffa
 *
 */
@Service
public class ProxyRequestFactory {

	@Autowired
	private List<ProxyRequest> proxyRequests;
	private static final Map<String, Map<String,ProxyRequest>> PROXY_REQUEST_CACHE= new HashMap<>();
	
	@PostConstruct
	public void initProxyRequestCache(){
		for(ProxyRequest proxyRequest:proxyRequests){
			PROXY_REQUEST_CACHE.putIfAbsent(proxyRequest.getSupportedHttpMethod().toLowerCase(),new HashMap<>());
			PROXY_REQUEST_CACHE.get(proxyRequest.getSupportedHttpMethod().toLowerCase()).put(proxyRequest.getSupportedContentType().toLowerCase(), proxyRequest);
		}
	}
	public ProxyRequest getProxyRequest(HttpServletRequest request) throws Exception{
		switch(request.getMethod().toUpperCase()){
		case "POST":
			return PROXY_REQUEST_CACHE.get(request.getMethod().toLowerCase()).get(request.getContentType().toLowerCase());
		case "GET":
			return PROXY_REQUEST_CACHE.get(request.getMethod().toLowerCase()).get("all");
		default:
			throw new Exception("Unhandled request method");
		}	
	}
}
