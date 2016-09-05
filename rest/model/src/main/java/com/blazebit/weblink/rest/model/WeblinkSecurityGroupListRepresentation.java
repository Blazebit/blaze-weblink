package com.blazebit.weblink.rest.model;

import java.io.Serializable;
import java.util.List;

public class WeblinkSecurityGroupListRepresentation implements Serializable {

	private static final long serialVersionUID = 1L;

	private OwnerRepresentation owner;
	private List<WeblinkSecurityGroupListElementRepresentation> securityGroups;

	public WeblinkSecurityGroupListRepresentation() {
	}

	public WeblinkSecurityGroupListRepresentation(OwnerRepresentation owner, List<WeblinkSecurityGroupListElementRepresentation> securityGroups) {
		this.owner = owner;
		this.securityGroups = securityGroups;
	}

	public OwnerRepresentation getOwner() {
		return owner;
	}

	public void setOwner(OwnerRepresentation owner) {
		this.owner = owner;
	}

	public List<WeblinkSecurityGroupListElementRepresentation> getSecurityGroups() {
		return securityGroups;
	}

	public void setSecurityGroups(List<WeblinkSecurityGroupListElementRepresentation> securityGroups) {
		this.securityGroups = securityGroups;
	}
}
