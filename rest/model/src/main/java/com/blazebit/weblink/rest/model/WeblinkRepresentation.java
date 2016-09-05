package com.blazebit.weblink.rest.model;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;

public class WeblinkRepresentation extends WeblinkUpdateRepresentation<ConfigurationTypeConfigElementRepresentation> {

	private static final long serialVersionUID = 1L;
	
	private Calendar creationDate;

	public WeblinkRepresentation() {
		super();
	}

	public WeblinkRepresentation(String targetUri, Calendar expirationTime, String securityGroupName, String dispatcherType, Set<ConfigurationTypeConfigElementRepresentation> dispatcherConfiguration, Map<String, String> tags, Calendar creationDate) {
		super(targetUri, expirationTime, securityGroupName, dispatcherType, dispatcherConfiguration, tags);
		this.creationDate = creationDate;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

}
