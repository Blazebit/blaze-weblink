package com.blazebit.weblink.server.weblinkgroup;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.AccountListElementRepresentation;
import com.blazebit.weblink.rest.model.ConfigurationTypeListElementRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

public class WeblinkGroupSupport {

	@Inject
	private BlazeWeblink client;

	@Produces
	@Named("keyStrategies")
	@RequestScoped
	public List<ConfigurationTypeListElementRepresentation> getKeyStrategies() {
		return client.keyStrategies().get();
	}
	
	@Produces
	@Named("keyStrategyItems")
	@RequestScoped
	public List<SelectItem> getKeyStrategyItems(@Named("keyStrategies") List<ConfigurationTypeListElementRepresentation> keyStrategies) {
		List<SelectItem> keyStrategyItems = new ArrayList<>(keyStrategies.size());
		
		for (ConfigurationTypeListElementRepresentation strategy: keyStrategies) {
			keyStrategyItems.add(new SelectItem(strategy.getKey(), strategy.getName()));
		}
		
		return keyStrategyItems;
	}

	@Produces
	@Named("matcherTypes")
	@RequestScoped
	public List<ConfigurationTypeListElementRepresentation> getMatcherTypes() {
		return client.matcherTypes().get();
	}

	@Produces
	@Named("matcherTypeItems")
	@RequestScoped
	public List<SelectItem> getMatcherTypeItems(@Named("matcherTypes") List<ConfigurationTypeListElementRepresentation> matcherTypes) {
		List<SelectItem> matcherTypeItems = new ArrayList<>(matcherTypes.size());

		for (ConfigurationTypeListElementRepresentation matcherType: matcherTypes) {
			matcherTypeItems.add(new SelectItem(matcherType.getKey(), matcherType.getName()));
		}

		return matcherTypeItems;
	}

}
