package com.blazebit.weblink.rest.impl.view;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingParameter;
import com.blazebit.persistence.view.MappingSingular;
import com.blazebit.weblink.core.api.WeblinkKeyStrategyFactoryDataAccess;
import com.blazebit.weblink.core.api.WeblinkMatcherFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.ConfigurationElement;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.model.jpa.WeblinkGroup;
import com.blazebit.weblink.rest.model.WeblinkGroupRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@EntityView(WeblinkGroup.class)
public abstract class WeblinkGroupRepresentationView extends WeblinkGroupRepresentation {

	private static final long serialVersionUID = 1L;

	public WeblinkGroupRepresentationView(
			@Mapping("id") String id,
			@Mapping("owner.key") String ownerKey,
			@Mapping("creationDate") Calendar creationDate,
			@Mapping("keyStrategyType") String keyStrategyType, 
			@Mapping("keyStrategyConfiguration") @MappingSingular Map<String, String> keyStrategyConfiguration, 
			@Mapping("matcherType") String matcherType, 
			@Mapping("matcherConfiguration") @MappingSingular Map<String, String> matcherConfiguration,
			@MappingParameter("keyStrategyDataAccess") WeblinkKeyStrategyFactoryDataAccess keyStrategyDataAccess,
			@MappingParameter("matcherDataAccess") WeblinkMatcherFactoryDataAccess matcherDataAccess) {
		super(keyStrategyType, fromMap(keyStrategyConfiguration, keyStrategyDataAccess.findByKey(keyStrategyType).getMetamodel()), matcherType, fromMap(matcherConfiguration, matcherDataAccess.findByKey(matcherType).getMetamodel()), id, ownerKey, creationDate);
	}
	
	@JsonIgnore
	@IdMapping("id")
	public abstract String getId();

	@JsonIgnore
	@Mapping("owner.id")
	public abstract Long getOwnerId();
	
	private static Set<ConfigurationTypeConfigElementRepresentation> fromMap(Map<String, String> map, ProviderMetamodel metamodel) {
		Set<ConfigurationTypeConfigElementRepresentation> set = new HashSet<>(map.size());
		
		for (Map.Entry<String, String> entry : map.entrySet()) {
			ConfigurationElement configElement = metamodel.getConfigurationElement(entry.getKey());
			set.add(new ConfigurationTypeConfigElementRepresentation(entry.getKey(), entry.getValue(), configElement.getType(), configElement.getName(), configElement.getDescription()));
		}
		
 		return set;
	}
	
}
