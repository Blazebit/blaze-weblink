package com.blazebit.weblink.rest.impl.context;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.blazebit.weblink.core.api.context.UserContext;

public class UserContextImpl implements UserContext {

	private final Long accountId;
	private final String accountKey;
	private final Set<String> accountRoles;
	private final Locale locale;
	private final List<Locale> locales;
	
	public UserContextImpl(Long accountId, String accountKey, Set<String> accountRoles, Locale locale, List<Locale> locales) {
		this.accountId = accountId;
		this.accountKey = accountKey;
		this.locale = locale;
		this.locales = locales;
		
		if (accountRoles == null) {
			this.accountRoles = Collections.emptySet();
		} else {
			this.accountRoles = accountRoles;
		}
	}

	@Override
	public Long getAccountId() {
		return accountId;
	}

	@Override
	public String getAccountKey() {
		return accountKey;
	}

	@Override
	public Set<String> getAccountRoles() {
		return accountRoles;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public List<Locale> getLocales() {
		return locales;
	}

}
