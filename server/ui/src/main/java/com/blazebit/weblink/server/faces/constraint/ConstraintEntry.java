package com.blazebit.weblink.server.faces.constraint;

import com.blazebit.weblink.server.faces.configuration.ConfigurationHolder;

import java.io.Serializable;

public class ConstraintEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;
	private ConfigurationHolder configurationHolder = new ConfigurationHolder();

	public ConstraintEntry() {
	}

	public ConstraintEntry(ConfigurationHolder configurationHolder) {
		this.configurationHolder = configurationHolder;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ConfigurationHolder getConfigurationHolder() {
		return configurationHolder;
	}
}
