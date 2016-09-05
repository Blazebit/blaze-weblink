package com.blazebit.weblink.server.securitygroup;

import com.blazebit.weblink.rest.model.WeblinkSecurityGroupRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SecurityGroupDetailPage extends SecurityGroupBasePage {

	private static final long serialVersionUID = 1L;
	
	public WeblinkSecurityGroupRepresentation getSecurityGroup() {
		return (WeblinkSecurityGroupRepresentation) securityGroup;
	}

}
