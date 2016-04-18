package com.blazebit.weblink.rest.api;

import javax.ws.rs.Path;

@Path("weblink-groups")
public interface WeblinkGroupsResource {

	@Path("")
	public WeblinkGroupsSubResource get();
}
