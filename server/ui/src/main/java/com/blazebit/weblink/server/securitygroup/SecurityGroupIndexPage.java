package com.blazebit.weblink.server.securitygroup;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.ConfigurationTypeListElementRepresentation;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupListElementRepresentation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class SecurityGroupIndexPage {

	@Inject
	private BlazeWeblink weblink;

	@Inject
	@Named("securityConstraintTypes")
	private List<ConfigurationTypeListElementRepresentation> securityConstraintTypes;

	protected String accountKey;
	protected Map<String, String> typeMap;
	
	@PostConstruct
	public void init() {
		this.typeMap = new HashMap<>(securityConstraintTypes.size());
		for (ConfigurationTypeListElementRepresentation element : securityConstraintTypes) {
			typeMap.put(element.getKey(), element.getName());
		}
	}
	
	@Produces
	@Named("securityGroupList")
	@RequestScoped
	public List<WeblinkSecurityGroupListElementRepresentation> createSecurityGroupList() {
		return weblink.accounts().get(accountKey).getSecurityGroups().get().getSecurityGroups();
	}
	
	public String getTypeName(String type) {
		return typeMap.get(type);
	}

	public String getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;
	}
}
