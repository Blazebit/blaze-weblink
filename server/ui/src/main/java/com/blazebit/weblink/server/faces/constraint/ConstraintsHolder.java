package com.blazebit.weblink.server.faces.constraint;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ConstraintsHolder {

	protected List<ConstraintEntry> constraintEntries = new ArrayList<>();

	public List<Set<ConfigurationTypeConfigEntryRepresentation>> getConfiguration() {
		List<Set<ConfigurationTypeConfigEntryRepresentation>> constraints = new ArrayList<>(constraintEntries.size());

		for (ConstraintEntry constraintEntry : constraintEntries) {
			Set<ConfigurationTypeConfigEntryRepresentation> configEntries = constraintEntry.getConfigurationHolder().getConfiguration();
			configEntries.add(new ConfigurationTypeConfigEntryRepresentation("type", constraintEntry.getType()));
			constraints.add(configEntries);
		}
		
		return constraints;
	}
	
	public void setConfiguration(List<Set<ConfigurationTypeConfigEntryRepresentation>> configuration) {
		constraintEntries = new ArrayList<>(configuration.size());
		for (Set<ConfigurationTypeConfigEntryRepresentation> configurationEntry : configuration) {
			ConstraintEntry entry = new ConstraintEntry();
			String type = null;

			Iterator<ConfigurationTypeConfigEntryRepresentation> configEntryIter = configurationEntry.iterator();
			while (configEntryIter.hasNext()) {
				ConfigurationTypeConfigEntryRepresentation configEntry = configEntryIter.next();
				if ("type".equals(configEntry.getKey())) {
					type = configEntry.getValue();
					configEntryIter.remove();
					break;
				}
			}

			if (type == null) {
				throw new IllegalArgumentException("Unexpected constraint configuration without a type!");
			}
			entry.setType(type);
			entry.getConfigurationHolder().setConfiguration(configurationEntry);
			constraintEntries.add(entry);
		}
	}

	public List<ConstraintEntry> getConstraintEntries() {
		return constraintEntries;
	}

	public void setConstraintEntries(List<ConstraintEntry> constraintEntries) {
		this.constraintEntries = constraintEntries;
	}
}
