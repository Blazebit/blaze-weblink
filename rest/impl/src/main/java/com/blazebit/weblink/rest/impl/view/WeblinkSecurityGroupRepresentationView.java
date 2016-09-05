package com.blazebit.weblink.rest.impl.view;

import com.blazebit.persistence.view.*;
import com.blazebit.weblink.core.api.WeblinkSecurityConstraintFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.ConfigurationElement;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraintFactory;
import com.blazebit.weblink.core.model.jpa.WeblinkSecurityGroup;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.URI;
import java.util.*;

@EntityView(WeblinkSecurityGroup.class)
public abstract class WeblinkSecurityGroupRepresentationView extends WeblinkSecurityGroupRepresentation {

	private static final long serialVersionUID = 1L;

	public WeblinkSecurityGroupRepresentationView(
			@Mapping("creationDate") Calendar creationDate,
			@Mapping("tags") Map<String, String> tags,
			@Mapping("constraintConfigurations") @MappingSingular List<Map<String, String>> constraintConfigurations,
			@MappingParameter("securityConstraintFactoryDataAccess") WeblinkSecurityConstraintFactoryDataAccess securityConstraintFactoryDataAccess) {
		super(toConfiguration(securityConstraintFactoryDataAccess, constraintConfigurations), tags, creationDate);
	}

	@JsonIgnore
	@IdMapping("id")
	public abstract Long getId();
	
	private static List<Set<ConfigurationTypeConfigElementRepresentation>> toConfiguration(WeblinkSecurityConstraintFactoryDataAccess securityConstraintFactoryDataAccess, List<Map<String, String>> constraintConfigurations) {
		List<Set<ConfigurationTypeConfigElementRepresentation>> result = new ArrayList<>(constraintConfigurations.size());

		for (Map<String, String> configMap : constraintConfigurations) {
			WeblinkSecurityConstraintFactory factory = securityConstraintFactoryDataAccess.findByKey(securityConstraintFactoryDataAccess.getType(configMap));
			if (factory == null) {
				throw new IllegalArgumentException("Unexpected constraint configuration without a type!");
			}

			ProviderMetamodel metamodel = factory.getMetamodel();
			Set<ConfigurationTypeConfigElementRepresentation> set = new LinkedHashSet<>(configMap.size());

			for (Map.Entry<String, String> entry : configMap.entrySet()) {
				ConfigurationElement configElement = metamodel.getConfigurationElement(entry.getKey());
				ConfigurationTypeConfigElementRepresentation resultConfig = new ConfigurationTypeConfigElementRepresentation();
				resultConfig.setKey(entry.getKey());
				resultConfig.setValue(entry.getValue());
				if (!"type".equals(entry.getKey())) {
					resultConfig.setType(configElement.getType());
					resultConfig.setName(configElement.getName());
					resultConfig.setDescription(configElement.getDescription());
				}
				set.add(resultConfig);
			}

			result.add(set);
		}
		
		return result;
	}
	
}
