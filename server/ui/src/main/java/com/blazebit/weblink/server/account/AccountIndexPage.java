package com.blazebit.weblink.server.account;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.AccountListElementRepresentation;

@Named
@RequestScoped
public class AccountIndexPage {

	@Inject
	private BlazeWeblink client;
	
	@Produces
	@Named("accountList")
	@RequestScoped
	public List<AccountListElementRepresentation> createAccountList() {
		return client.accounts().get();
	}
}
