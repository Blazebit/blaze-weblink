package com.blazebit.weblink.examples.localhost.client;

import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.client.BlazeWeblinkClient;

public class AlwaysAdminBlazeWeblinkProducer {

	@Inject
	private HttpServletRequest httpServletRequest;
	
	// This is just for test purposes, DO NOT USE IN PRODUCTION!
	@Produces
	@RequestScoped
	public BlazeWeblink createClient() {
		URI uri = URI.create(httpServletRequest.getRequestURL().toString());
		String contextPath = httpServletRequest.getContextPath();
		String basePath = "/api";
		
		if (contextPath != null) {
			basePath = contextPath + basePath;
		}
		
		try {
			uri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), basePath, null, null);
	    } catch (URISyntaxException x) {
	        throw new IllegalArgumentException(x.getMessage(), x);
	    }
		
		return BlazeWeblinkClient.getInstance(uri.toString());
	}
}
