package com.blazebit.weblink.rest.model;

import java.io.Serializable;
import java.util.Calendar;

public class WeblinkGroupListElementRepresentation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String ownerKey;
	private Calendar creationDate;

	public WeblinkGroupListElementRepresentation() {
	}

	public WeblinkGroupListElementRepresentation(String name, String ownerKey, Calendar creationDate) {
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
