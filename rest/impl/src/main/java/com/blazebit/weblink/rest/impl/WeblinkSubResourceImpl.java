package com.blazebit.weblink.rest.impl;

import java.net.URI;
import java.util.LinkedHashMap;
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
import com.blazebit.weblink.core.api.WeblinkDataAccess;
import com.blazebit.weblink.core.api.WeblinkDispatcherFactoryDataAccess;
import com.blazebit.weblink.core.api.WeblinkNotFoundException;
import com.blazebit.weblink.core.api.WeblinkService;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcher;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcherFactory;
import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkGroup;
import com.blazebit.weblink.core.model.jpa.WeblinkId;
import com.blazebit.weblink.core.model.security.Role;
import com.blazebit.weblink.rest.api.WeblinkSubResource;
import com.blazebit.weblink.rest.impl.view.WeblinkDispatchView;
import com.blazebit.weblink.rest.impl.view.WeblinkRepresentationView;
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
			return Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity("Bucket object not found").build();
		}
	}

	@Override
	public Response put(WeblinkUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation> weblinkUpdate) {
		Weblink weblink = new Weblink(weblinkId);
		
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
