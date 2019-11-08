/**
 * 
 */
package com.http.proxy.httpproxy.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author muffa
 *
 */
public class PostProxyRequestFactory {

	public static ProxyRequest getProxyRequest(HttpServletRequest request) throws Exception{
		switch(request.getContentType()){
		case "application/json":
			return new JsonPostProxyRequest(request);
		case "application/x-www-form-urlencoded":
			return new FormPostProxyRequest(request);
		default:
			throw new Exception("unhandled content type");
		}	
	}
}
