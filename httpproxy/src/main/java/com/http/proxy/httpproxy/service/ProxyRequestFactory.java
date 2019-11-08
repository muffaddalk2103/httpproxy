/**
 * 
 */
package com.http.proxy.httpproxy.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author muffa
 *
 */
public class ProxyRequestFactory {

	public static ProxyRequest getProxyRequest(HttpServletRequest request) throws Exception{
		switch(request.getMethod().toUpperCase()){
		case "POST":
			return PostProxyRequestFactory.getProxyRequest(request);
		case "GET":
			return new GetProxyRequest(request);
		default:
			throw new Exception("Unhandled request method");
		}	
	}
}
