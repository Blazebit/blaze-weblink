package com.blazebit.weblink.rest.impl;

import java.util.List;

import javax.inject.Inject;

import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.AccountDataAccess;
import com.blazebit.weblink.rest.api.AccountSubResource;
import com.blazebit.weblink.rest.api.AccountsResource;
import com.blazebit.weblink.rest.impl.view.AccountListElementRepresentationView;
import com.blazebit.weblink.rest.model.AccountListElementRepresentation;

public class AccountsResourceImpl extends AbstractResource implements AccountsResource {

	@Inject
	private AccountDataAccess accountDataAccess;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AccountListElementRepresentation> get() {
		return (List<AccountListElementRepresentation>) (List<?>) accountDataAccess.findAll(EntityViewSetting.create(AccountListElementRepresentationView.class));
	}

	@Override
	public AccountSubResource get(String id) {
		return inject(new AccountSubResourceImpl(id));
	}

}
