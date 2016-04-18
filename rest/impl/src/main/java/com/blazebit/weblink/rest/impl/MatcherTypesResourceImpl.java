package com.blazebit.weblink.rest.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.blazebit.weblink.core.api.WeblinkMatcherFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkMatcherFactory;
import com.blazebit.weblink.rest.api.MatcherTypesResource;

@ApplicationScoped
public class MatcherTypesResourceImpl extends AbstractProviderResourceImpl implements MatcherTypesResource {
	
	private final List<ProviderMetamodel> metamodelList = new ArrayList<>();
	private final Map<String, ProviderMetamodel> metamodelMap = new HashMap<>();
	
	@Inject
	private WeblinkMatcherFactoryDataAccess weblinkMatcherFactoryDataAccess;
	
	@PostConstruct
	public void init() {
		for (WeblinkMatcherFactory factory : weblinkMatcherFactoryDataAccess.findAll()) {
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
