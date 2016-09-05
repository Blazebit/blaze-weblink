package com.blazebit.weblink.server.securitygroup;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupUpdateRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;
import com.blazebit.weblink.server.faces.configuration.ConfigurationEntry;
import com.blazebit.weblink.server.faces.configuration.ConfigurationHolder;
import com.blazebit.weblink.server.faces.constraint.ConstraintEntry;
import com.blazebit.weblink.server.faces.constraint.ConstraintsHolder;
import com.blazebit.weblink.server.faces.tag.TagEntry;
import com.blazebit.weblink.server.faces.tag.TagsHolder;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecurityGroupBasePage implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SecurityGroupBasePage.class.getName());

	@Inject
	protected BlazeWeblink client;
	@Inject
	protected FacesContext facesContext;

	protected String accountKey;
	protected String name;
	protected TagsHolder tagsHolder = new TagsHolder();
	protected ConstraintsHolder constraintsHolder = new ConstraintsHolder();
	protected WeblinkSecurityGroupUpdateRepresentation<? extends ConfigurationTypeConfigEntryRepresentation> securityGroup = new WeblinkSecurityGroupUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation>();

	@PostConstruct
	private void postConstruct() {
		constraintsHolder.getConstraintEntries().add(new ConstraintEntry());
	}

	public String viewAction() {
		try {
			if (name != null && !name.isEmpty()) {
				securityGroup = client.accounts().get(accountKey).getSecurityGroups().get(name).get();
				if (securityGroup == null) {
					tagsHolder.setTagEntries(new ArrayList<TagEntry>());
					constraintsHolder.setConfiguration(new ArrayList<Set<ConfigurationTypeConfigEntryRepresentation>>());
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No security group found for name " + name, null));
					init();
					return null;
				} else {
					tagsHolder.setTags(securityGroup.getTags());
					constraintsHolder.setConfiguration((List<Set<ConfigurationTypeConfigEntryRepresentation>>) (List) securityGroup.getConfiguration());
					init();
					return null;
				}
			}

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid empty name!", null));
			return null;
		} catch (RuntimeException ex) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not load security group", null));
			LOG.log(Level.SEVERE, "Could not load security group " + name, ex);
			return null;
		}
	}

	public List<ConfigurationTypeConfigElementRepresentation> createConfiguration(String type) {
		return client.securityConstraintTypes().get(type).getConfiguration();
	}
	
	protected void init() {
	}

	protected void put() {
		WeblinkSecurityGroupUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation> newSecurityGroup = new WeblinkSecurityGroupUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation>();
		newSecurityGroup.setTags(tagsHolder.getTags());
		newSecurityGroup.setConfiguration(constraintsHolder.getConfiguration());
		Response r = client.accounts().get(accountKey).getSecurityGroups().get(name).put(newSecurityGroup, accountKey);
		if (r.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while saving security group: " + name, null));
			return;
		}
		securityGroup = newSecurityGroup;
	}

	public String delete() {
		Response r = client.accounts().get(accountKey).getSecurityGroups().get(name).delete();
		if (r.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not delete security group: " + name, null));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "/security-group/edit.xhtml?account=" + accountKey + "&name=" + name + "&faces-redirect=true";
		}
		return "/security-group/index.xhtml?" + "account=" + accountKey + "&faces-redirect=true";
	}

	public String getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WeblinkSecurityGroupUpdateRepresentation<? extends ConfigurationTypeConfigEntryRepresentation> getSecurityGroup() {
		return securityGroup;
	}

	public TagsHolder getTagsHolder() {
		return tagsHolder;
	}

	public ConstraintsHolder getConstraintsHolder() {
		return constraintsHolder;
	}

}
