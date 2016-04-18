package com.blazebit.weblink.rest.client;

import com.blazebit.weblink.rest.api.AccountsResource;
import com.blazebit.weblink.rest.api.AdminResource;
import com.blazebit.weblink.rest.api.DispatcherTypesResource;
import com.blazebit.weblink.rest.api.KeyStrategiesResource;
import com.blazebit.weblink.rest.api.MatcherTypesResource;
import com.blazebit.weblink.rest.api.WeblinkGroupsSubResource;

public interface BlazeWeblink {

	public AdminResource admin();

	public AccountsResource accounts();

	public WeblinkGroupsSubResource weblinkGroups();

	public MatcherTypesResource matcherTypes();

	public DispatcherTypesResource dispatcherTypes();

	public KeyStrategiesResource keyStrategies();

	public void close();

}