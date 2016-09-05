package com.blazebit.weblink.rest.impl.view;

import java.net.URI;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingParameter;
import com.blazebit.persistence.view.MappingSingular;
import com.blazebit.weblink.core.api.WeblinkDispatcherFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.ConfigurationElement;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkId;
import com.blazebit.weblink.rest.model.WeblinkRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@EntityView(Weblink.class)
public abstract class WeblinkRepresentationView extends WeblinkRepresentation {
	
	private static final long serialVersionUID = 1L;

	public WeblinkRepresentationView(
			@Mapping("targetUri") URI targetUri, 
			@Mapping("expirationTime") Calendar expirationTime,
			@Mapping("weblinkSecurityGroup.name") String securityGroupName,
			@Mapping("dispatcherType") String dispatcherType,
			@Mapping("dispatcherConfiguration") @MappingSingular Map<String, String> dispatcherConfiguration, 
			@Mapping("tags") Map<String, String> tags,
			@Mapping("creationDate") Calendar creationDate,
			@MappingParameter("dispatcherDataAccess") WeblinkDispatcherFactoryDataAccess dispatcherDataAccess) {
		super(targetUri.toString(), expirationTime, securityGroupName, dispatcherType, fromMap(dispatcherConfiguration, dispatcherDataAccess.findByKey(dispatcherType).getMetamodel()), tags, creationDate);
	}

	@JsonIgnore
	@IdMapping("id")
	public abstract WeblinkId getId();

	@JsonIgnore
	@Mapping("weblinkGroup.owner.id")
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
