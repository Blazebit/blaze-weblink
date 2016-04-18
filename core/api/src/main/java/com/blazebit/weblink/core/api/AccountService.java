package com.blazebit.weblink.core.api;

import com.blazebit.weblink.core.model.jpa.Account;

public interface AccountService {

	public void create(Account account);

	public void update(Account account);

	public void delete(long accountId);
}
