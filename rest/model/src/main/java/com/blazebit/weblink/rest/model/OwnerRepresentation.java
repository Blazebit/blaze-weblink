package com.blazebit.weblink.rest.model;

import java.io.Serializable;

public class OwnerRepresentation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String key;
	private String displayName;

	public OwnerRepresentation() {
	}

	public OwnerRepresentation(String key, String displayName) {
		this.key = key;
		this.displayName = displayName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
