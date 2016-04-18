package com.blazebit.weblink.rest.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.blazebit.weblink.rest.model.ConfigurationTypeListElementRepresentation;
import com.blazebit.weblink.rest.model.ProviderConfigurationRepresentation;

@RolesAllowed({ "admin" })
@Path("matcher-types")
public interface MatcherTypesResource {
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ConfigurationTypeListElementRepresentation> get();

	@GET
	@Path("{type}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ProviderConfigurationRepresentation get(@PathParam("type") String type);
}
