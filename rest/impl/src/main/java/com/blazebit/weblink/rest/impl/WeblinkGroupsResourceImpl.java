package com.blazebit.weblink.rest.impl;

import com.blazebit.weblink.rest.api.WeblinkGroupsResource;
import com.blazebit.weblink.rest.api.WeblinkGroupsSubResource;

public class WeblinkGroupsResourceImpl extends AbstractResource implements WeblinkGroupsResource {

	@Override
	public WeblinkGroupsSubResource get() {
		return inject(new WeblinkGroupsSubResourceImpl(userContext.getAccountId()));
	}

}
