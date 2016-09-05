package com.blazebit.weblink.rest.impl;

import java.net.URI;
import java.util.*;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.*;
import com.blazebit.weblink.core.model.jpa.Account;
import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkGroup;
import com.blazebit.weblink.core.model.jpa.WeblinkId;
import com.blazebit.weblink.core.model.security.Role;
import com.blazebit.weblink.rest.api.WeblinkGroupSubResource;
import com.blazebit.weblink.rest.api.WeblinkGroupsResource;
import com.blazebit.weblink.rest.api.WeblinkGroupsSubResource;
import com.blazebit.weblink.rest.api.WeblinkSubResource;
import com.blazebit.weblink.rest.impl.view.BulkWeblinkRepresentationView;
import com.blazebit.weblink.rest.impl.view.WeblinkGroupRepresentationView;
import com.blazebit.weblink.rest.model.BlazeWeblinkHeaders;
import com.blazebit.weblink.rest.model.BulkWeblinkRepresentation;
import com.blazebit.weblink.rest.model.WeblinkGroupRepresentation;
import com.blazebit.weblink.rest.model.WeblinkGroupUpdateRepresentation;
import com.blazebit.weblink.rest.model.WeblinkUpdateRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

public class WeblinkGroupSubResourceImpl extends AbstractResource implements WeblinkGroupSubResource {

	private static final Logger LOG = Logger.getLogger(WeblinkGroupSubResourceImpl.class.getName());
	
	private final long accountId;
	private final String weblinkGroupId;
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private AccountDataAccess accountDataAccess;
	@Inject
	private WeblinkGroupDataAccess weblinkGroupDataAccess;
	@Inject
	private WeblinkGroupService weblinkGroupService;
	@Inject
	private WeblinkKeyStrategyFactoryDataAccess weblinkKeyStrategyFactoryDataAccess;
	@Inject
	private WeblinkMatcherFactoryDataAccess weblinkMatcherFactoryDataAccess;
	@Inject
	private WeblinkService weblinkService;
	@Inject
	private WeblinkDataAccess weblinkDataAccess;
	@Inject
	private WeblinkDispatcherFactoryDataAccess weblinkDispatcherFactoryDataAccess;
	@Inject
	private WeblinkSecurityGroupDataAccess securityGroupDataAccess;

	public WeblinkGroupSubResourceImpl(long accountId, String bucketId) {
		this.accountId = accountId;
		this.weblinkGroupId = bucketId;
	}

	@Override
	public WeblinkGroupRepresentation get() {
		EntityViewSetting<WeblinkGroupRepresentationView, CriteriaBuilder<WeblinkGroupRepresentationView>> setting;
		setting = EntityViewSetting.create(WeblinkGroupRepresentationView.class);
		setting.addOptionalParameter("keyStrategyDataAccess", weblinkKeyStrategyFactoryDataAccess);
		setting.addOptionalParameter("matcherDataAccess", weblinkMatcherFactoryDataAccess);
		WeblinkGroupRepresentationView result = weblinkGroupDataAccess.findByName(weblinkGroupId, setting);
		if (result == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
		}
		if (!result.getOwnerId().equals(accountId) && !userContext.getAccountRoles().contains(Role.ADMIN)) {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).build());
		}
		
		return result;
	}

	@Override
	public Response delete() {
		throw new WebApplicationException(Response.status(Status.NOT_IMPLEMENTED).type(MediaType.TEXT_PLAIN).entity("Not yet implemented").build());
	}

	@Override
	public Response put(WeblinkGroupUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation, ConfigurationTypeConfigEntryRepresentation> weblinkGroupUpdate, String ownerKey) {
		WeblinkGroup weblinkGroup = new WeblinkGroup(weblinkGroupId);
		
		if (ownerKey == null || ownerKey.isEmpty() || ownerKey.equals(userContext.getAccountKey())) {
			weblinkGroup.setOwner(new Account(accountId));
		} else if (userContext.getAccountRoles().contains(Role.ADMIN)) {
			Account owner = accountDataAccess.findByKey(ownerKey);
			
			if (owner == null) {
				throw new WebApplicationException(Response.status(Status.BAD_REQUEST).header(BlazeWeblinkHeaders.ERROR_CODE, "AccountNotFound").type(MediaType.TEXT_PLAIN_TYPE).entity("Account does not exist").build());
			}
			
			weblinkGroup.setOwner(owner);
		} else {
			throw new WebApplicationException(Response.status(Status.FORBIDDEN).type(MediaType.TEXT_PLAIN_TYPE).entity("Only admins may change the owner").build());
		}

		// TODO: changing the key strategy should actually not be allowed
		weblinkGroup.setKeyStrategyType(weblinkGroupUpdate.getKeyStrategyType());
		weblinkGroup.setKeyStrategyConfiguration(toMap(weblinkGroupUpdate.getKeyStrategyConfiguration()));
		
		weblinkGroup.setMatcherType(weblinkGroupUpdate.getMatcherType());
		weblinkGroup.setMatcherConfiguration(toMap(weblinkGroupUpdate.getMatcherConfiguration()));
		
		weblinkGroupService.put(weblinkGroup);
		return Response.ok().build();
	}
	
	private static Map<String, String> toMap(Set<ConfigurationTypeConfigEntryRepresentation> configurationEntries) {
		if (configurationEntries == null) {
			return Collections.emptyMap();
		}

		Map<String, String> configurationMap = new LinkedHashMap<>(configurationEntries.size());
		for (ConfigurationTypeConfigEntryRepresentation entry : configurationEntries) {
			configurationMap.put(entry.getKey(), entry.getValue());
		}
		
		return configurationMap;
	}

	@Override
	public Response createWeblink(WeblinkUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation> weblinkUpdate, String ownerKey) {
		Weblink weblink = new Weblink(new WeblinkId(weblinkGroupId, null));
		Account owner = new Account(accountId);

		weblink.setWeblinkGroup(new WeblinkGroup(weblinkGroupId));

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
		weblink.setDispatcherConfiguration(toMap(weblinkUpdate.getDispatcherConfiguration()));

		String securityGroupName = weblinkUpdate.getSecurityGroupName();
		if (securityGroupName != null && !securityGroupName.isEmpty()) {
			weblink.setWeblinkSecurityGroup(securityGroupDataAccess.findByOwnerAndName(owner, securityGroupName));
		}
		
		weblink.setTags(weblinkUpdate.getTags());
		weblink.setTargetUri(URI.create(weblinkUpdate.getTargetUri()));
		
		String key = weblinkService.create(weblink);
		
		return Response.created(uriInfo.getBaseUriBuilder()
				.path(WeblinkGroupsResource.class)
				.path(WeblinkGroupsSubResource.class, "getGroup")
				.path(WeblinkGroupSubResource.class, "getWeblink")
				.build(weblinkGroupId, key))
				.build();
	}

	@Override
	public WeblinkSubResource getWeblink(String key) {
		return inject(new WeblinkSubResourceImpl(accountId, weblinkGroupId, key));
	}

	@Override
	public List<BulkWeblinkRepresentation> getAllWeblinks(List<String> weblinkKeys) {
		EntityViewSetting<BulkWeblinkRepresentationView, CriteriaBuilder<BulkWeblinkRepresentationView>> setting = EntityViewSetting.create(BulkWeblinkRepresentationView.class);
		setting.addOptionalParameter("dispatcherDataAccess", weblinkDispatcherFactoryDataAccess);
		if (weblinkKeys == null || weblinkKeys.isEmpty()) {
			return (List<BulkWeblinkRepresentation>) (List<?>) weblinkDataAccess.findAllByWeblinkGroup(weblinkGroupId, setting);
		} else {
			List<WeblinkId> weblinkIds = new ArrayList<>();
			for (String weblinkKey : weblinkKeys) {
				weblinkIds.add(new WeblinkId(weblinkGroupId, weblinkKey));
			}
			return (List<BulkWeblinkRepresentation>) (List<?>) weblinkDataAccess.findByIds(weblinkIds, setting);
		}
	}

}
