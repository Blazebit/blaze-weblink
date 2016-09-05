package com.blazebit.weblink.rest.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.blazebit.weblink.rest.model.BulkWeblinkRepresentation;
import com.blazebit.weblink.rest.model.WeblinkGroupRepresentation;
import com.blazebit.weblink.rest.model.WeblinkGroupUpdateRepresentation;
import com.blazebit.weblink.rest.model.WeblinkUpdateRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

public interface WeblinkGroupSubResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public WeblinkGroupRepresentation get();

	/**
	 * This implementation of the DELETE operation deletes the weblink group named in
	 * the URI. All weblinks in the weblink group must be deleted before the weblink group itself can be deleted.
	 * 
	 * @return
	 */
	@DELETE
	public Response delete();
	
	/**
	 * Creates or updates the weblink group.
	 * 
	 * @param weblinkGroupUpdate
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(WeblinkGroupUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation, ConfigurationTypeConfigEntryRepresentation> weblinkGroupUpdate, @HeaderParam("x-blz-owner-key") String ownerKey);

	@POST
	@Path("weblink")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createWeblink(WeblinkUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation> weblinkUpdate, @HeaderParam("x-blz-owner-key") String ownerKey);
	
	@Path("{key}")
	public WeblinkSubResource getWeblink(@PathParam("key") String key);
	
	@GET
	@Path("weblinks")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<BulkWeblinkRepresentation> getAllWeblinks(@QueryParam("weblinkKey") List<String> weblinkKeys);
}
