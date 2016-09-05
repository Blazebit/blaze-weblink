package com.blazebit.weblink.rest.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.blazebit.weblink.rest.model.WeblinkRepresentation;
import com.blazebit.weblink.rest.model.WeblinkUpdateRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

public interface WeblinkSubResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public WeblinkRepresentation get();

	@GET
	@Path("dispatch")
	public Response dispatch();

	// This is for CORS, we can do that later
	// @OPTIONS
	// public Response options();

	/**
	 * This implementation of the DELETE operation marks the weblink named in the
	 * URI (including all weblink versions) as deleted. If the weblink doesn't
	 * exist this does nothing.
	 * 
	 * @return
	 */
	@DELETE
	public Response delete();

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response put(WeblinkUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation> weblinkUpdate, @HeaderParam("x-blz-owner-key") String ownerKey);

}
