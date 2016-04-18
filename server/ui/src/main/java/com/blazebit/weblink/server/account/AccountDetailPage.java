package com.blazebit.weblink.server.account;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.blazebit.weblink.rest.model.AccountRepresentation;

@Named
@RequestScoped
public class AccountDetailPage extends AccountBasePage {

	private static final long serialVersionUID = 1L;
	
	public AccountRepresentation getAccount() {
		return (AccountRepresentation) account;
	}
	
}
