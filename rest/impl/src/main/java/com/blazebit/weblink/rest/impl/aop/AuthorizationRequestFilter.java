package com.blazebit.weblink.rest.impl.aop;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import com.blazebit.weblink.core.api.context.UserContext;
import com.blazebit.weblink.core.model.security.Role;

public class AuthorizationRequestFilter implements ContainerRequestFilter {
	
	@Inject
	private UserContext userContext;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (!userContext.getAccountRoles().contains(Role.USER)) {
			throw new WebApplicationException("User cannot access the resource.", Response.Status.UNAUTHORIZED);
		}
	}
}