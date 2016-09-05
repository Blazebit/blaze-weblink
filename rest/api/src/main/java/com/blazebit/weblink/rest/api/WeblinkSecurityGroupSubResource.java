package com.blazebit.weblink.rest.api;


import com.blazebit.weblink.rest.model.WeblinkSecurityGroupRepresentation;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupUpdateRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface WeblinkSecurityGroupSubResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public WeblinkSecurityGroupRepresentation get();

	@DELETE
	public Response delete();
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response put(WeblinkSecurityGroupUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation> securityGroupUpdate, @HeaderParam("x-blz-owner-key") String ownerKey);
	
}
