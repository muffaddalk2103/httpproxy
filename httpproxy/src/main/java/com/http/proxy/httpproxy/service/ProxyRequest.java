/**
 * 
 */
package com.http.proxy.httpproxy.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

/**
 * @author muffa
 *
 */
public interface ProxyRequest {
	ResponseEntity<?> processRequest() throws IOException;
}
