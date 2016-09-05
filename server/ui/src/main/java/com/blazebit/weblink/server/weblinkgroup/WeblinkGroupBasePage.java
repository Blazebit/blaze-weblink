package com.blazebit.weblink.server.weblinkgroup;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.model.WeblinkGroupRepresentation;
import com.blazebit.weblink.rest.model.WeblinkGroupUpdateRepresentation;
import com.blazebit.weblink.server.faces.configuration.ConfigurationHolder;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeblinkGroupBasePage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WeblinkGroupBasePage.class.getName());

	@Inject
	protected BlazeWeblink client;
	@Inject
	protected FacesContext facesContext;

	protected String account;
	protected String name;
	protected ConfigurationHolder keyStrategyConfigurationHolder = new ConfigurationHolder();
	protected ConfigurationHolder matcherConfigurationHolder = new ConfigurationHolder();
	protected WeblinkGroupUpdateRepresentation weblinkGroup = new WeblinkGroupRepresentation();

	public String viewAction() {
		try {
			if (name != null && !name.isEmpty()) {
				weblinkGroup = client.weblinkGroups().getGroup(name).get();
				if (weblinkGroup == null) {
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No weblink group found for name " + name, null));
					init();
					return null;
				} else {
					init();
					return null;
				}
			}

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid empty name!", null));
			return null;
		} catch (RuntimeException ex) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not load weblink group", null));
			LOG.log(Level.SEVERE, "Could not load weblinkGroup " + name, ex);
			return null;
		}
	}
	
	protected void init() {
	}

	public void onKeyStrategyTypeChanged() {
		keyStrategyConfigurationHolder.setConfiguration(client.keyStrategies().get(weblinkGroup.getKeyStrategyType()).getConfiguration());
	}

	public void onMatcherTypeChanged() {
		matcherConfigurationHolder.setConfiguration(client.matcherTypes().get(weblinkGroup.getMatcherType()).getConfiguration());
	}
	
	public void put() {
		WeblinkGroupUpdateRepresentation newWeblinkGroup = new WeblinkGroupUpdateRepresentation();
		newWeblinkGroup.setKeyStrategyType(weblinkGroup.getKeyStrategyType());
		newWeblinkGroup.setKeyStrategyConfiguration(weblinkGroup.getKeyStrategyConfiguration());
		newWeblinkGroup.setMatcherType(weblinkGroup.getMatcherType());
		newWeblinkGroup.setMatcherConfiguration(weblinkGroup.getMatcherConfiguration());
		client.weblinkGroups().getGroup(name).put(newWeblinkGroup, account);
		weblinkGroup = newWeblinkGroup;
	}

	public WeblinkGroupUpdateRepresentation getWeblinkGroup() {
		return weblinkGroup;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConfigurationHolder getKeyStrategyConfigurationHolder() {
		return keyStrategyConfigurationHolder;
	}

	public ConfigurationHolder getMatcherConfigurationHolder() {
		return matcherConfigurationHolder;
	}
}
