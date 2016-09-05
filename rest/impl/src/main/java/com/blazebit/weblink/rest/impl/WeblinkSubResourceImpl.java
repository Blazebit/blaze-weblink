package com.blazebit.weblink.rest.impl;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.*;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcher;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcherFactory;
import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraint;
import com.blazebit.weblink.core.model.jpa.Account;
import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkGroup;
import com.blazebit.weblink.core.model.jpa.WeblinkId;
import com.blazebit.weblink.core.model.security.Role;
import com.blazebit.weblink.rest.api.WeblinkSubResource;
import com.blazebit.weblink.rest.impl.view.WeblinkDispatchView;
import com.blazebit.weblink.rest.impl.view.WeblinkRepresentationView;
import com.blazebit.weblink.rest.model.BlazeWeblinkHeaders;
import com.blazebit.weblink.rest.model.WeblinkRepresentation;
import com.blazebit.weblink.rest.model.WeblinkUpdateRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

public class WeblinkSubResourceImpl extends AbstractResource implements WeblinkSubResource {

	private final long accountId;
	private final WeblinkId weblinkId;
	
	@Context
	private HttpServletRequest request;
	
	@Inject
	private WeblinkDataAccess weblinkDataAccess;
	@Inject
	private WeblinkService weblinkService;
	@Inject
	private WeblinkDispatcherFactoryDataAccess weblinkDispatcherFactoryDataAccess;
	@Inject
	private WeblinkSecurityGroupDataAccess securityGroupDataAccess;
	
	public WeblinkSubResourceImpl(long accountId, String weblinkGroupId, String key) {
		this.accountId = accountId;
		this.weblinkId = new WeblinkId(new WeblinkGroup(weblinkGroupId), key);
	}

	@Override
	public WeblinkRepresentation get() {
		EntityViewSetting<WeblinkRepresentationView, CriteriaBuilder<WeblinkRepresentationView>> setting;
		setting = EntityViewSetting.create(WeblinkRepresentationView.class);
		setting.addOptionalParameter("dispatcherDataAccess", weblinkDispatcherFactoryDataAccess);
		WeblinkRepresentationView result = weblinkDataAccess.findById(weblinkId, setting);
		if (result == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
		}
		if (!result.getOwnerId().equals(accountId) && !userContext.getAccountRoles().contains(Role.ADMIN)) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).build());
		}
		
		return result;
	}

	@Override
	public Response dispatch() {
		EntityViewSetting<WeblinkDispatchView, CriteriaBuilder<WeblinkDispatchView>> setting;
		setting = EntityViewSetting.create(WeblinkDispatchView.class);
		WeblinkDispatchView result = weblinkDataAccess.findByIdForDispatch(weblinkId, setting);
		if (result == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
		}

		if (result.getSecurityGroupId() != null) {
			List<WeblinkSecurityConstraint> constraints = securityGroupDataAccess.getSecurityGroupConstraints(result.getSecurityGroupId(), result.getSecurityGroupConfiguration());
			for (WeblinkSecurityConstraint constraint : constraints) {
				Response response = constraint.validate(result.getId());
				if (response != null) {
					return response;
				}
			}
		}
		
		WeblinkDispatcherFactory dispatcherFactory = weblinkDispatcherFactoryDataAccess.findByKey(result.getDispatcherType());
		WeblinkDispatcher dispatcher = dispatcherFactory.createWeblinkDispatcher(result.getDispatcherConfiguration());
		
		return dispatcher.dispatch(result.getTargetUri());
	}

	@Override
	public Response delete() {
		try {
			weblinkService.delete(weblinkId);
			return Response.noContent().build();
		} catch (WeblinkNotFoundException ex) {
			return Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity("Bucket weblink not found").build();
		}
	}

	@Override
	public Response put(WeblinkUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation> weblinkUpdate, String ownerKey) {
		Weblink weblink = new Weblink(weblinkId);

		// TODO: implement owner of a weblink
//		if (ownerKey == null || ownerKey.isEmpty() || ownerKey.equals(userContext.getAccountKey())) {
//			weblink.setOwner(owner);
//		} else if (userContext.getAccountRoles().contains(Role.ADMIN)) {
//			Account owner = accountDataAccess.findByKey(ownerKey);
//
//			if (owner == null) {
//				throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).header(BlazeWeblinkHeaders.ERROR_CODE, "AccountNotFound").type(MediaType.TEXT_PLAIN_TYPE).entity("Account does not exist").build());
//			}
//
//			weblink.setOwner(owner);
//		} else {
//			throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN).type(MediaType.TEXT_PLAIN_TYPE).entity("Only admins may change the owner").build());
//		}
		
		weblink.setDispatcherType(weblinkUpdate.getDispatcherType());
//		WeblinkDispatcherFactory dispatcherFactory = weblinkDispatcherFactoryDataAccess.findByKey(weblink.getDispatcherType());
		weblink.setDispatcherConfiguration(toMap(weblinkUpdate.getDispatcherConfiguration()));
//		dispatcherFactory.createWeblinkDispatcher(weblink.getDispatcherConfiguration());

		weblink.setTags(weblinkUpdate.getTags());
		weblink.setTargetUri(URI.create(weblinkUpdate.getTargetUri()));
		
		weblinkService.put(weblink);
		return Response.ok().build();
	}
	
	private static Map<String, String> toMap(Set<ConfigurationTypeConfigEntryRepresentation> configurationEntries) {
		Map<String, String> configurationMap = new LinkedHashMap<>(configurationEntries.size());
		for (ConfigurationTypeConfigEntryRepresentation entry : configurationEntries) {
			configurationMap.put(entry.getKey(), entry.getValue());
		}
		
		return configurationMap;
	}

}
