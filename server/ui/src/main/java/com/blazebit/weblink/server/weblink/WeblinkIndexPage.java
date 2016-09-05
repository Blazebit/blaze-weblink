package com.blazebit.weblink.server.weblink;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.BulkWeblinkRepresentation;
import com.blazebit.weblink.server.account.AccountSupport;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class WeblinkIndexPage {

	@Inject
	private BlazeWeblink client;
	
	@Inject
	private AccountSupport accountSupport;
	
	private String group;
	private String account;
	
	@Produces
	@Named("weblinkList")
	@RequestScoped
	public List<BulkWeblinkRepresentation> createWeblinkList() {
		return client.weblinkGroups().getGroup(group).getAllWeblinks(null);
	}
	
	public String getAccountName(String ownerKey) {
		return accountSupport.getAccountName(ownerKey);
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
