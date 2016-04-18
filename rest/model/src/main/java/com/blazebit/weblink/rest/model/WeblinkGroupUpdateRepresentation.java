package com.blazebit.weblink.rest.model;

import java.io.Serializable;
import java.util.Set;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

public class WeblinkGroupUpdateRepresentation<Y extends ConfigurationTypeConfigEntryRepresentation, X extends ConfigurationTypeConfigEntryRepresentation> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String keyStrategyType;
	private Set<X> keyStrategyConfiguration;
	private String matcherType;
	private Set<Y> matcherConfiguration;

	public WeblinkGroupUpdateRepresentation() {
	}

	public WeblinkGroupUpdateRepresentation(String keyStrategyType, Set<X> keyStrategyConfiguration, String matcherType, Set<Y> matcherConfiguration) {
		this.keyStrategyType = keyStrategyType;
		this.keyStrategyConfiguration = keyStrategyConfiguration;
		this.matcherType = matcherType;
		this.matcherConfiguration = matcherConfiguration;
	}

	public String getKeyStrategyType() {
		return keyStrategyType;
	}

	public void setKeyStrategyType(String keyStrategyType) {
		this.keyStrategyType = keyStrategyType;
	}

	public Set<X> getKeyStrategyConfiguration() {
		return keyStrategyConfiguration;
	}

	public void setKeyStrategyConfiguration(Set<X> keyStrategyConfiguration) {
		this.keyStrategyConfiguration = keyStrategyConfiguration;
	}

	public String getMatcherType() {
		return matcherType;
	}

	public void setMatcherType(String matcherType) {
		this.matcherType = matcherType;
	}

	public Set<Y> getMatcherConfiguration() {
		return matcherConfiguration;
	}

	public void setMatcherConfiguration(Set<Y> matcherConfiguration) {
		this.matcherConfiguration = matcherConfiguration;
	}

}
