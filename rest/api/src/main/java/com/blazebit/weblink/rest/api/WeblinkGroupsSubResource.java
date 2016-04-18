package com.blazebit.weblink.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.blazebit.weblink.rest.model.WeblinkGroupListElementRepresentation;

public interface WeblinkGroupsSubResource {
	
	/**
	 * Returns the weblink groups of the current user.
	 * 
	 * @return
	 */
	@GET
	@Path("")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<WeblinkGroupListElementRepresentation> get();

	/**
	 * Returns the weblink group with the given id.
	 * 
	 * @return
	 */
	@Path("{id}")
	public WeblinkGroupSubResource getGroup(@PathParam("id") String id);
	
}
