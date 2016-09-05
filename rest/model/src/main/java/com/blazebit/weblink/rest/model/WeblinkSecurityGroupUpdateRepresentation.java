package com.blazebit.weblink.rest.model;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

import java.io.Serializable;
import java.util.*;

public class WeblinkSecurityGroupUpdateRepresentation<T extends ConfigurationTypeConfigEntryRepresentation> implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Set<T>> configuration = new ArrayList<>(0);
	private Map<String, String> tags = new HashMap<String, String>(0);

	public WeblinkSecurityGroupUpdateRepresentation() {
		super();
	}

	public WeblinkSecurityGroupUpdateRepresentation(List<Set<T>> configuration, Map<String, String> tags) {
		this.configuration = configuration;
		this.tags = tags;
	}

	public List<Set<T>> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(List<Set<T>> configuration) {
		this.configuration = configuration;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}
}
