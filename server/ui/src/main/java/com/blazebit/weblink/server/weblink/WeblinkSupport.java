package com.blazebit.weblink.server.weblink;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.ConfigurationTypeListElementRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

public class WeblinkSupport {

	@Inject
	private BlazeWeblink client;
	
	@Produces
	@Named("dispatcherTypes")
	@RequestScoped
	public List<ConfigurationTypeListElementRepresentation> getDispatcherTypes() {
		return client.dispatcherTypes().get();
	}
	
	@Produces
	@Named("dispatcherTypeItems")
	@RequestScoped
	public List<SelectItem> getSecurityConstraintTypeItems(@Named("dispatcherTypes") List<ConfigurationTypeListElementRepresentation> dispatcherTypes) {
		List<SelectItem> dispatcherTypeItems = new ArrayList<>(dispatcherTypes.size());
		
		for (ConfigurationTypeListElementRepresentation dispatcherType : dispatcherTypes) {
			dispatcherTypeItems.add(new SelectItem(dispatcherType.getKey(), dispatcherType.getName(), dispatcherType.getDescription()));
		}
		
		return dispatcherTypeItems;
	}
}
