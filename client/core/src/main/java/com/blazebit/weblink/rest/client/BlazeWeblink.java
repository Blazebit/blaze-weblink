package com.blazebit.weblink.rest.client;

import com.blazebit.weblink.rest.api.*;

public interface BlazeWeblink {

	public String getExternalLink(String weblinkGroupName, String weblinkKey);

	public AdminResource admin();

	public AccountsResource accounts();

	public WeblinkGroupsSubResource weblinkGroups();

	public MatcherTypesResource matcherTypes();

	public DispatcherTypesResource dispatcherTypes();

	public KeyStrategiesResource keyStrategies();

	public SecurityConstraintTypesResource securityConstraintTypes();

	public void close();

}