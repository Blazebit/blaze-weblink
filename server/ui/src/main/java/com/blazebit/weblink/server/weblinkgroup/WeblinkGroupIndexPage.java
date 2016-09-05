package com.blazebit.weblink.server.weblinkgroup;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.WeblinkGroupListElementRepresentation;
import com.blazebit.weblink.server.account.AccountSupport;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class WeblinkGroupIndexPage {

	@Inject
	private BlazeWeblink client;
	
	@Inject
	private AccountSupport accountSupport;
	
	private boolean admin;
	private String account;
	
	@Produces
	@Named("weblinkGroupList")
	@RequestScoped
	public List<WeblinkGroupListElementRepresentation> createWeblinkGroupList() {
		if (admin) {
			return client.admin().getWeblinkGroups();
		} else {
			if (account != null) {
				return client.accounts().get(account).getWeblinkGroups();
			} else {
				return client.weblinkGroups().get();
			}
		}
	}
	
	public String getAccountName(String ownerKey) {
		return accountSupport.getAccountName(ownerKey);
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
