package com.blazebit.weblink.server.weblink;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.WeblinkRepresentation;
import com.blazebit.weblink.rest.model.WeblinkUpdateRepresentation;
import com.blazebit.weblink.server.faces.configuration.ConfigurationEntry;
import com.blazebit.weblink.server.faces.configuration.ConfigurationHolder;
import com.blazebit.weblink.server.faces.tag.TagEntry;
import com.blazebit.weblink.server.faces.tag.TagsHolder;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeblinkBasePage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WeblinkBasePage.class.getName());

	@Inject
	protected BlazeWeblink client;
	@Inject
	protected FacesContext facesContext;

	private String ownerAccount;
	protected String group;
	protected String key;
	protected String account;
	protected TagsHolder tagsHolder = new TagsHolder();
	protected ConfigurationHolder dispatcherConfigurationHolder = new ConfigurationHolder();
	protected WeblinkUpdateRepresentation weblink = new WeblinkRepresentation();

	public String viewAction() {
		try {
			if (group != null && !group.isEmpty() && key != null && !key.isEmpty()) {
				weblink = client.weblinkGroups().getGroup(group).getWeblink(key).get();
				if (weblink == null) {
					tagsHolder.setTagEntries(new ArrayList<TagEntry>());
					dispatcherConfigurationHolder.setConfigurationEntries(new ArrayList<ConfigurationEntry>());
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No weblink found for key " + key, null));
					init();
					return null;
				} else {
					tagsHolder.setTags(weblink.getTags());
					dispatcherConfigurationHolder.setConfiguration(weblink.getDispatcherConfiguration());
					init();
					return "";
				}
			}

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid empty key!", null));
			return null;
		} catch (RuntimeException ex) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not load weblink", null));
			LOG.log(Level.SEVERE, "Could not load weblink " + key, ex);
			return null;
		}
	}
	
	protected void init() {
	}

	public void onDispatcherTypeChanged() {
		dispatcherConfigurationHolder.setConfiguration(client.dispatcherTypes().get(weblink.getDispatcherType()).getConfiguration());
	}

	public void put() {
		WeblinkUpdateRepresentation newWeblink = new WeblinkUpdateRepresentation();
		newWeblink.setTargetUri(weblink.getTargetUri());
		newWeblink.setExpirationTime(weblink.getExpirationTime());
		newWeblink.setDispatcherType(weblink.getDispatcherType());
		newWeblink.setDispatcherConfiguration(dispatcherConfigurationHolder.getConfiguration());
		newWeblink.setTags(tagsHolder.getTags());
		client.weblinkGroups().getGroup(group).getWeblink(key).put(newWeblink, ownerAccount);
		weblink = newWeblink;
	}
	
	public WeblinkUpdateRepresentation getWeblink() {
		return weblink;
	}

	public TagsHolder getTagsHolder() {
		return tagsHolder;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOwnerAccount() {
		return ownerAccount;
	}

	public void setOwnerAccount(String ownerAccount) {
		this.ownerAccount = ownerAccount;
	}

	public ConfigurationHolder getDispatcherConfigurationHolder() {
		return dispatcherConfigurationHolder;
	}
}
