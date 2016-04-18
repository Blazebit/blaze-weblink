package com.blazebit.weblink.core.impl;

import javax.ejb.Stateless;

import com.blazebit.weblink.core.api.AccountService;
import com.blazebit.weblink.core.model.jpa.Account;

@Stateless
public class AccountServiceImpl extends AbstractService implements AccountService {

	@Override
	public void create(Account account) {
		em.persist(account);
		em.flush();
	}

	@Override
	public void update(Account account) {
		em.merge(account);
	}

	@Override
	public void delete(long accountId) {
		throw new UnsupportedOperationException("Deletion of accounts not yet supported!");
	}

}
