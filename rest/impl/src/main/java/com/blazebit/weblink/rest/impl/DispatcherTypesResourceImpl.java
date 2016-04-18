package com.blazebit.weblink.rest.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.blazebit.weblink.core.api.WeblinkDispatcherFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcherFactory;
import com.blazebit.weblink.rest.api.DispatcherTypesResource;

@ApplicationScoped
public class DispatcherTypesResourceImpl extends AbstractProviderResourceImpl implements DispatcherTypesResource {
	
	private final List<ProviderMetamodel> metamodelList = new ArrayList<>();
	private final Map<String, ProviderMetamodel> metamodelMap = new HashMap<>();
	
	@Inject
	private WeblinkDispatcherFactoryDataAccess weblinkDispatcherFactoryDataAccess;
	
	@PostConstruct
	public void init() {
		for (WeblinkDispatcherFactory factory : weblinkDispatcherFactoryDataAccess.findAll()) {
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
