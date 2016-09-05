package com.blazebit.weblink.server.securitygroup;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.ConfigurationTypeListElementRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

public class SecurityGroupSupport {

	@Inject
	private BlazeWeblink client;
	
	@Produces
	@Named("securityConstraintTypes")
	@RequestScoped
	public List<ConfigurationTypeListElementRepresentation> getSecurityConstraintTypes() {
		return client.securityConstraintTypes().get();
	}
	
	@Produces
	@Named("securityConstraintTypeItems")
	@RequestScoped
	public List<SelectItem> getSecurityConstraintTypeItems(@Named("securityConstraintTypes") List<ConfigurationTypeListElementRepresentation> securityConstraintTypes) {
		List<SelectItem> securityConstraintTypeItems = new ArrayList<>(securityConstraintTypes.size());
		
		for (ConfigurationTypeListElementRepresentation storageType : securityConstraintTypes) {
			securityConstraintTypeItems.add(new SelectItem(storageType.getKey(), storageType.getName(), storageType.getDescription()));
		}
		
		return securityConstraintTypeItems;
	}
}
