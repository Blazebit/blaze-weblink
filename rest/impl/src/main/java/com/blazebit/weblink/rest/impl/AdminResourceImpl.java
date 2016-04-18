package com.blazebit.weblink.rest.impl;

import java.util.List;

import javax.inject.Inject;

import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.WeblinkGroupDataAccess;
import com.blazebit.weblink.rest.api.AdminResource;
import com.blazebit.weblink.rest.impl.view.WeblinkGroupListElementRepresentationView;
import com.blazebit.weblink.rest.model.WeblinkGroupListElementRepresentation;

public class AdminResourceImpl extends AbstractResource implements AdminResource {

	@Inject
	private WeblinkGroupDataAccess weblinkGroupDataAccess;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<WeblinkGroupListElementRepresentation> getWeblinkGroups() {
		return (List<WeblinkGroupListElementRepresentation>) (List<?>) weblinkGroupDataAccess.findAll(EntityViewSetting.create(WeblinkGroupListElementRepresentationView.class));
	}

}
