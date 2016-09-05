package com.blazebit.weblink.rest.model;

import java.io.Serializable;
import java.util.Calendar;

public class WeblinkSecurityGroupListElementRepresentation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Calendar creationDate;

	public WeblinkSecurityGroupListElementRepresentation() {
	}

	public WeblinkSecurityGroupListElementRepresentation(String name, Calendar creationDate) {
		this.name = name;
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

}
