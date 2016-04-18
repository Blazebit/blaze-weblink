package com.blazebit.weblink.rest.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.blazebit.weblink.rest.model.WeblinkGroupListElementRepresentation;

@RolesAllowed({ "admin" })
@Path("admin")
public interface AdminResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("weblink-groups")
	public List<WeblinkGroupListElementRepresentation> getWeblinkGroups();
}
