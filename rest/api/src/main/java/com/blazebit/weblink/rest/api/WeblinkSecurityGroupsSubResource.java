package com.blazebit.weblink.rest.api;


import com.blazebit.weblink.rest.api.aop.Authenticated;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupListRepresentation;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupUpdateRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Authenticated
public interface WeblinkSecurityGroupsSubResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public WeblinkSecurityGroupListRepresentation get();

	@Path("{securityGroupName}")
	public WeblinkSecurityGroupSubResource get(@PathParam("securityGroupName") String securityGroupName);
}
