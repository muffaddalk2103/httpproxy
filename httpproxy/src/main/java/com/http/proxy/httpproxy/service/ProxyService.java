/**
 * 
 */
package com.http.proxy.httpproxy.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author muffa
 *
 */
@Service
public class ProxyService {
	private final static Logger LOGGER = LoggerFactory.getLogger(ProxyService.class); 
	private final Set<String> NOT_ACCEPTED_HEADERS;

	@Autowired
	public ProxyService(@Value("${not.accepted.header}") final String strs) {
		NOT_ACCEPTED_HEADERS = new HashSet<>(Arrays.asList(strs.split(",")));
	}
	public ResponseEntity<?> processGetRequest(String URL, HttpServletRequest httpServletRequest) throws ParseException, IOException{
		LOGGER.info("inside processGetRequest of ProxyService");
		HttpGet request = new HttpGet(URL);
		prepareHeader(httpServletRequest, request);
		return processRequest(request);
	}

	public ResponseEntity<?> processPostRequest(String URL, HttpServletRequest httpServletRequest) throws ParseException, IOException{
		LOGGER.info("inside processPostRequest of ProxyService");
		HttpPost request = new HttpPost(URL);
		prepareHeader(httpServletRequest, request);
		switch(httpServletRequest.getContentType()){
		case "application/json":
			request.setEntity(new StringEntity(IOUtils.toString(httpServletRequest.getReader())));
			break;
		case "application/x-www-form-urlencoded":
			List<NameValuePair> urlParameters = new ArrayList<>();
			Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
			while(parameterNames.hasMoreElements()){
				String parameterName = parameterNames.nextElement();
				urlParameters.add(new BasicNameValuePair(parameterName, httpServletRequest.getParameter(parameterName)));	
			}
			request.setEntity(new UrlEncodedFormEntity(urlParameters));
			break;
		}
		return processRequest(request);
	}

	private void prepareHeader(HttpServletRequest httpServletRequest,HttpUriRequest request){
		LOGGER.info("inside prepareHeader of ProxyService");
		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String headerName = headerNames.nextElement();
			if(!NOT_ACCEPTED_HEADERS.contains(headerName.toLowerCase())){
				request.addHeader(headerName, httpServletRequest.getHeader(headerName));
			}
		}
	}
	private ResponseEntity<?> processRequest(HttpUriRequest request) throws IOException{
		LOGGER.info("inside processRequest of ProxyService");
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(request)) {
			String result=null;
			HttpEntity entity = (HttpEntity) response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity);
				final HttpHeaders responseHeaders = new HttpHeaders();
				List<Header> headers= Arrays.asList(response.getAllHeaders());
				headers.stream().forEach(e -> responseHeaders.put(e.getName(), java.util.Arrays.asList(e.getValue())));
				return new ResponseEntity<String>(result,responseHeaders, HttpStatus.valueOf(response.getStatusLine().getStatusCode())); 
			}else{
				return new ResponseEntity<String>("Unable to process request",HttpStatus.BAD_GATEWAY); 
			}
		}
	}
}
