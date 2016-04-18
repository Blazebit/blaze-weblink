package com.blazebit.weblink.rest.impl;

import java.util.List;

import javax.inject.Inject;

import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.WeblinkGroupDataAccess;
import com.blazebit.weblink.rest.api.WeblinkGroupSubResource;
import com.blazebit.weblink.rest.api.WeblinkGroupsSubResource;
import com.blazebit.weblink.rest.impl.view.WeblinkGroupListElementRepresentationView;
import com.blazebit.weblink.rest.model.WeblinkGroupListElementRepresentation;

public class WeblinkGroupsSubResourceImpl extends AbstractResource implements WeblinkGroupsSubResource {
	
	@Inject
	private WeblinkGroupDataAccess weblinkGroupDataAccess;
	
	private final long accountId;

	public WeblinkGroupsSubResourceImpl(long accountId) {
		this.accountId = accountId;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WeblinkGroupListElementRepresentation> get() {
		return (List<WeblinkGroupListElementRepresentation>) (List<?>) weblinkGroupDataAccess.findByAccountId(accountId, EntityViewSetting.create(WeblinkGroupListElementRepresentationView.class));
	}

	@Override
	public WeblinkGroupSubResource getGroup(String id) {
		return inject(new WeblinkGroupSubResourceImpl(accountId, id));
	}


}
