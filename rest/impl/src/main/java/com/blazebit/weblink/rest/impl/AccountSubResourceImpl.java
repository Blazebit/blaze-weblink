package com.blazebit.weblink.rest.impl;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.AccountDataAccess;
import com.blazebit.weblink.core.api.AccountService;
import com.blazebit.weblink.core.api.WeblinkGroupDataAccess;
import com.blazebit.weblink.core.model.jpa.Account;
import com.blazebit.weblink.rest.api.AccountSubResource;
import com.blazebit.weblink.rest.api.AccountsResource;
import com.blazebit.weblink.rest.impl.view.AccountRepresentationView;
import com.blazebit.weblink.rest.impl.view.WeblinkGroupListElementRepresentationView;
import com.blazebit.weblink.rest.model.AccountRepresentation;
import com.blazebit.weblink.rest.model.AccountUpdateRepresentation;
import com.blazebit.weblink.rest.model.WeblinkGroupListElementRepresentation;

public class AccountSubResourceImpl extends AbstractResource implements AccountSubResource {
	
	private String key;
	
	@Inject
	private AccountService accountService;
	@Inject
	private AccountDataAccess accountDataAccess;
	@Inject
	private WeblinkGroupDataAccess weblinkGroupDataAccess;

	public AccountSubResourceImpl(String key) {
		this.key = key;
	}

	@Override
	public AccountRepresentation get() {
		AccountRepresentation result = accountDataAccess.findByKey(key, EntityViewSetting.create(AccountRepresentationView.class));
		if (result == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity("Account not found").build());
		}
		
		return result;
	}

	@Override
	public Response put(AccountUpdateRepresentation accountUpdate) {
		Account account = getAccountByKey(key);
		boolean isNew = account == null;
		
		if (isNew) {
			account = new Account();
			account.setKey(key);
		}
		
		account.setName(accountUpdate.getName());
		account.setTags(accountUpdate.getTags());
		
		if (isNew) {
			accountService.create(account);
		} else {
			accountService.update(account);
		}
		
		URI uri = uriInfo.getRequestUriBuilder()
			.path(AccountsResource.class, "get")
			.build(account.getId());
		return Response.created(uri).build();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<WeblinkGroupListElementRepresentation> getWeblinkGroups() {
		return (List<WeblinkGroupListElementRepresentation>) (List<?>) weblinkGroupDataAccess.findByAccountId(getAccountByKey(key).getId(), EntityViewSetting.create(WeblinkGroupListElementRepresentationView.class));
	}
	
	private Account getAccountByKey(String key) {
		return accountDataAccess.findByKey(key);
	}

}
