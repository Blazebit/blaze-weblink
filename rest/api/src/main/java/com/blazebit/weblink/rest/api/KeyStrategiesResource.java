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
@Path("key-strategies")
public interface KeyStrategiesResource {
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ConfigurationTypeListElementRepresentation> get();

	@GET
	@Path("{strategy}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ProviderConfigurationRepresentation get(@PathParam("strategy") String strategy);
}
