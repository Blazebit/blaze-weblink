package com.blazebit.weblink.rest.impl;

import com.blazebit.weblink.core.api.WeblinkSecurityConstraintFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraintFactory;
import com.blazebit.weblink.rest.api.SecurityConstraintTypesResource;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SecurityConstraintTypesResourceImpl extends AbstractProviderResourceImpl implements SecurityConstraintTypesResource {
	
	private final List<ProviderMetamodel> metamodelList = new ArrayList<>();
	private final Map<String, ProviderMetamodel> metamodelMap = new HashMap<>();
	
	@Inject
	private WeblinkSecurityConstraintFactoryDataAccess weblinkSecurityConstraintFactoryDataAccess;
	
	@PostConstruct
	public void init() {
		for (WeblinkSecurityConstraintFactory factory : weblinkSecurityConstraintFactoryDataAccess.findAll()) {
			ProviderMetamodel metamodel = factory.getMetamodel();
			metamodelList.add(metamodel);
			metamodelMap.put(metamodel.getKey(), metamodel);
		}
	}

	@Override
	protected List<ProviderMetamodel> getMetamodelList() {
		return metamodelList;
	}

	@Override
	protected Map<String, ProviderMetamodel> getMetamodelMap() {
		return metamodelMap;
	}

}
