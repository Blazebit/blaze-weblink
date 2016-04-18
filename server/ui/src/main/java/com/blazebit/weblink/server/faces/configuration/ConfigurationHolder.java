package com.blazebit.weblink.server.faces.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

public class ConfigurationHolder {

	protected List<ConfigurationEntry> configurationEntries = new ArrayList<>();
	
	public Set<ConfigurationTypeConfigEntryRepresentation> getConfiguration() {
		Set<ConfigurationTypeConfigEntryRepresentation> configuration = new LinkedHashSet<>(configurationEntries.size());
		FacesContext context = FacesContext.getCurrentInstance();
		boolean valid = true;
		
		for (ConfigurationEntry entry : configurationEntries) {
			if (entry.getKey() == null || entry.getKey().isEmpty()) {
				if (entry.getValue() != null && !entry.getValue().isEmpty()) {
					// TODO: I18N messages
					String validatorMessageString = "Entry with empty key found for value: " + entry.getValue();
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessageString, validatorMessageString);
	                context.addMessage(null, message);
	                valid = false;
				}
				
				continue;
			}
			if (!configuration.add(new ConfigurationTypeConfigEntryRepresentation(entry.getKey(), entry.getValue()))) {
				// TODO: I18N messages
				String validatorMessageString = "Duplicate configuration key found: " + entry.getKey();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessageString, validatorMessageString);
                context.addMessage(null, message);
                valid = false;
			}
		}
		
		if (!valid) {
			throw new RuntimeException("Invalid configuration!");
		}
		
		return configuration;
	}
	
	public void setConfiguration(Collection<? extends ConfigurationTypeConfigEntryRepresentation> configuration) {
		configurationEntries = new ArrayList<>(configuration.size());
		for (ConfigurationTypeConfigEntryRepresentation entry : configuration) {
			if (entry instanceof ConfigurationTypeConfigElementRepresentation) {
				ConfigurationTypeConfigElementRepresentation element = (ConfigurationTypeConfigElementRepresentation) entry;
				configurationEntries.add(new ConfigurationEntry(entry.getKey(), entry.getValue(), element.getType(), element.getName(), element.getDescription()));
			} else {
				configurationEntries.add(new ConfigurationEntry(entry.getKey(), entry.getValue()));
			}
		}
	}

	public List<ConfigurationEntry> getConfigurationEntries() {
		return configurationEntries;
	}

	public void setConfigurationEntries(List<ConfigurationEntry> configurationEntries) {
		this.configurationEntries = configurationEntries;
	}
}
