package com.blazebit.weblink.core.api.spi;

import com.blazebit.weblink.core.model.jpa.WeblinkId;

import javax.ws.rs.core.Response;


public interface WeblinkSecurityConstraint {
	
	public Response validate(WeblinkId weblinkId);
	
}
