package com.blazebit.weblink.rest.model;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;

public class BulkWeblinkRepresentation extends WeblinkRepresentation {

	private static final long serialVersionUID = 1L;
	
	private String weblinkKey;
	
	public BulkWeblinkRepresentation() {
		super();
	}

	public BulkWeblinkRepresentation(String targetUri, Calendar expirationTime, String dispatcherType, Set<ConfigurationTypeConfigElementRepresentation> dispatcherConfiguration, Map<String, String> tags, Calendar creationDate, String weblinkKey) {
		super(targetUri, expirationTime, dispatcherType, dispatcherConfiguration, tags, creationDate);
		this.weblinkKey = weblinkKey;
	}

	public String getWeblinkKey() {
		return weblinkKey;
	}

	public void setWeblinkKey(String weblinkKey) {
		this.weblinkKey = weblinkKey;
	}

}
