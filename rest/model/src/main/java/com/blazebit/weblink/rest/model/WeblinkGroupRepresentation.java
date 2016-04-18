package com.blazebit.weblink.rest.model;

import java.util.Calendar;
import java.util.Set;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;

public class WeblinkGroupRepresentation extends WeblinkGroupUpdateRepresentation<ConfigurationTypeConfigElementRepresentation, ConfigurationTypeConfigElementRepresentation> {

	private static final long serialVersionUID = 1L;

	private String name;
	private String ownerKey;
	private Calendar creationDate;

	public WeblinkGroupRepresentation() {
	}

	public WeblinkGroupRepresentation(String keyStrategyType, Set<ConfigurationTypeConfigElementRepresentation> keyStrategyConfiguration, String matcherType, Set<ConfigurationTypeConfigElementRepresentation> matcherConfiguration, String name, String ownerKey, Calendar creationDate) {
		super(keyStrategyType, keyStrategyConfiguration, matcherType, matcherConfiguration);
		this.name = name;
		this.ownerKey = ownerKey;
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerKey() {
		return ownerKey;
	}

	public void setOwnerKey(String ownerKey) {
		this.ownerKey = ownerKey;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

}
