package com.blazebit.weblink.rest.api;

import com.blazebit.weblink.rest.model.ConfigurationTypeListElementRepresentation;
import com.blazebit.weblink.rest.model.ProviderConfigurationRepresentation;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RolesAllowed({ "admin" })
@Path("security-constraint-types")
public interface SecurityConstraintTypesResource {
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ConfigurationTypeListElementRepresentation> get();

	@GET
	@Path("{type}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ProviderConfigurationRepresentation get(@PathParam("type") String type);
}
