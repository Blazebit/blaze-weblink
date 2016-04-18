package com.blazebit.weblink.modules.dispatcher.redirect;

import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import com.blazebit.weblink.core.api.spi.WeblinkDispatcher;
import com.blazebit.weblink.modules.dispatcher.base.AbstractWeblinkDispatcher;

public class RedirectWeblinkDispatcher extends AbstractWeblinkDispatcher implements WeblinkDispatcher {
	
	private static final Logger LOG = Logger.getLogger(RedirectWeblinkDispatcher.class.getName());

	@Override
	public Response dispatch(URI targetUri) {
		return Response.temporaryRedirect(targetUri).build();
	}
	

}
