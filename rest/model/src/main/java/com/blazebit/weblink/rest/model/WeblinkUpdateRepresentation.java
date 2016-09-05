package com.blazebit.weblink.rest.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

public class WeblinkUpdateRepresentation<X extends ConfigurationTypeConfigEntryRepresentation> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String targetUri;
	private Calendar expirationTime;
	private String securityGroupName;
	private String dispatcherType;
	private Set<X> dispatcherConfiguration = new LinkedHashSet<>(0);
	private Map<String, String> tags = new HashMap<String, String>(0);
	
	public WeblinkUpdateRepresentation() {
	}

	public WeblinkUpdateRepresentation(String targetUri, Calendar expirationTime, String securityGroupName, String dispatcherType, Set<X> dispatcherConfiguration, Map<String, String> tags) {
		this.targetUri = targetUri;
		this.expirationTime = expirationTime;
		this.securityGroupName = securityGroupName;
		this.dispatcherType = dispatcherType;
		this.dispatcherConfiguration = dispatcherConfiguration;
		this.tags = tags;
	}

	public String getTargetUri() {
		return targetUri;
	}

	public void setTargetUri(String targetUri) {
		this.targetUri = targetUri;
	}

	public Calendar getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Calendar expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getSecurityGroupName() {
		return securityGroupName;
	}

	public void setSecurityGroupName(String securityGroupName) {
		this.securityGroupName = securityGroupName;
	}

	public String getDispatcherType() {
		return dispatcherType;
	}

	public void setDispatcherType(String dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

	public Set<X> getDispatcherConfiguration() {
		return dispatcherConfiguration;
	}

	public void setDispatcherConfiguration(Set<X> dispatcherConfiguration) {
		this.dispatcherConfiguration = dispatcherConfiguration;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}
}
