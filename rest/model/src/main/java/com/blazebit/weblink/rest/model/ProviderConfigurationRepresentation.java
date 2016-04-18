package com.blazebit.weblink.rest.model;

import java.util.ArrayList;
import java.util.List;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;

public class ProviderConfigurationRepresentation extends ConfigurationTypeListElementRepresentation {

	private static final long serialVersionUID = 1L;

	private List<ConfigurationTypeConfigElementRepresentation> configuration = new ArrayList<>(0);

	public ProviderConfigurationRepresentation() {
	}

	public List<ConfigurationTypeConfigElementRepresentation> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(List<ConfigurationTypeConfigElementRepresentation> configuration) {
		this.configuration = configuration;
	}
	
}
