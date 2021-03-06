package com.blazebit.weblink.server.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.AccountUpdateRepresentation;
import com.blazebit.weblink.server.faces.tag.TagEntry;
import com.blazebit.weblink.server.faces.tag.TagsHolder;

public class AccountBasePage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(AccountBasePage.class.getName());

	@Inject
	protected BlazeWeblink client;
	@Inject
	protected FacesContext facesContext;
	
	protected String key;
	protected TagsHolder tagsHolder = new TagsHolder();
	protected AccountUpdateRepresentation account = new AccountUpdateRepresentation();

	public String viewAction() {
		try {
			if (key != null && !key.isEmpty()) {
				account = client.accounts().get(key).get();
				if (account == null) {
					tagsHolder.setTagEntries(new ArrayList<TagEntry>());
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No account found for key " + key, null));
					return null;
				} else {
					tagsHolder.setTags(account.getTags());
					return null;
				}
			}

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid empty key!", null));
			return null;
		} catch (RuntimeException ex) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not load account", null));
			LOG.log(Level.SEVERE, "Could not load account " + key, ex);
			return null;
		}
	}
	
	public void put() {
		AccountUpdateRepresentation newAccount = new AccountUpdateRepresentation();
		newAccount.setName(account.getName());
		newAccount.setTags(tagsHolder.getTags());
		client.accounts().get(key).put(newAccount);
		account = newAccount;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public AccountUpdateRepresentation getAccount() {
		return account;
	}

	public TagsHolder getTagsHolder() {
		return tagsHolder;
	}
}
