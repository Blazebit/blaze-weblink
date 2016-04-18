package com.blazebit.weblink.core.api.spi;

import java.net.URI;

import javax.ws.rs.core.Response;


public interface WeblinkDispatcher {
	
	public Response dispatch(URI targetUri);
	
}
