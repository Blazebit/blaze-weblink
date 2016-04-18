package com.blazebit.weblink.modules.dispatcher.forward;

import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.blazebit.weblink.core.api.spi.WeblinkDispatcher;
import com.blazebit.weblink.modules.dispatcher.base.AbstractWeblinkDispatcher;

public class ForwardWeblinkDispatcher extends AbstractWeblinkDispatcher implements WeblinkDispatcher {
	
	private static final Logger LOG = Logger.getLogger(ForwardWeblinkDispatcher.class.getName());

	@Override
	public Response dispatch(URI targetUri) {
		// Other methods?
		String method = "GET";
		
		WebTarget target = ClientBuilder.newClient().target(targetUri);
		Invocation invocation = target.request().build(method);
		Response r = invocation.invoke();
		
		ResponseBuilder b = Response.status(r.getStatus());
		
		b.type(r.getMediaType());
		b.entity(r.getEntity());
		
		// Other headers?
		
		return b.build();
	}

}
