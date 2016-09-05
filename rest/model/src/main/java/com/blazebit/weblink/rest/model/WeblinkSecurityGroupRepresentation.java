package com.blazebit.weblink.rest.model;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WeblinkSecurityGroupRepresentation extends WeblinkSecurityGroupUpdateRepresentation<ConfigurationTypeConfigElementRepresentation> {

	private static final long serialVersionUID = 1L;

	private Calendar creationDate;

	public WeblinkSecurityGroupRepresentation() {
	}

	public WeblinkSecurityGroupRepresentation(List<Set<ConfigurationTypeConfigElementRepresentation>> configuration, Map<String, String> tags, Calendar creationDate) {
		super(configuration, tags);
		this.creationDate = creationDate;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

}
