package com.blazebit.weblink.rest.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.blazebit.weblink.core.api.spi.ConfigurationElement;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.rest.model.ConfigurationTypeListElementRepresentation;
import com.blazebit.weblink.rest.model.ProviderConfigurationRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;

@ApplicationScoped
public abstract class AbstractProviderResourceImpl extends AbstractResource {
	
	protected abstract List<ProviderMetamodel> getMetamodelList();
	
	protected abstract Map<String, ProviderMetamodel> getMetamodelMap();
	
	public List<ConfigurationTypeListElementRepresentation> get() {
		List<ProviderMetamodel> metamodelList = getMetamodelList();
		List<ConfigurationTypeListElementRepresentation> list = new ArrayList<>(metamodelList.size());
		
		for (ProviderMetamodel metamodel : metamodelList) {
			ConfigurationTypeListElementRepresentation element = new ConfigurationTypeListElementRepresentation();
			element.setKey(metamodel.getKey());
			element.setName(metamodel.getName());
			element.setDescription(metamodel.getDescription());
			list.add(element);
		}
		
		return list;
	}

	public ProviderConfigurationRepresentation get(String type) {
		Map<String, ProviderMetamodel> metamodelMap = getMetamodelMap();
		ProviderMetamodel metamodel = metamodelMap.get(type);
		
		if (metamodel == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN_TYPE).entity("Could not find provider type!").build());
		}

		ProviderConfigurationRepresentation element = new ProviderConfigurationRepresentation();
		element.setKey(metamodel.getKey());
		element.setName(metamodel.getName());
		element.setDescription(metamodel.getDescription());
		
		Set<ConfigurationElement> configElements = metamodel.getConfigurationElements();
		List<ConfigurationTypeConfigElementRepresentation> providerTypeConfigs = new ArrayList<>(configElements.size());
		
		for (ConfigurationElement config : configElements) {
			ConfigurationTypeConfigElementRepresentation providerTypeConfig = new ConfigurationTypeConfigElementRepresentation();
			providerTypeConfig.setKey(config.getKey());
			providerTypeConfig.setType(config.getType());
			providerTypeConfig.setValue(config.getDefaultValue());
			providerTypeConfig.setName(config.getName());
			providerTypeConfig.setDescription(config.getDescription());
			providerTypeConfigs.add(providerTypeConfig);
		}
		
		element.setConfiguration(providerTypeConfigs);
		return element;
	}

}
