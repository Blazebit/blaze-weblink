package com.blazebit.weblink.rest.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.blazebit.weblink.rest.model.AccountRepresentation;
import com.blazebit.weblink.rest.model.AccountUpdateRepresentation;
import com.blazebit.weblink.rest.model.WeblinkGroupListElementRepresentation;

public interface AccountSubResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public AccountRepresentation get();
	
	// TODO: not yet planned
//	@DELETE
//	public Response delete();
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response put(AccountUpdateRepresentation account);

	@Path("security-groups")
	public WeblinkSecurityGroupsSubResource getSecurityGroups();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("weblink-groups")
	public List<WeblinkGroupListElementRepresentation> getWeblinkGroups();
}
