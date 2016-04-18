package com.blazebit.weblink.modules.dispatcher.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.blazebit.weblink.core.api.spi.ConfigurationElement;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;

public class DefaultProviderMetamodel implements ProviderMetamodel {
	
	private final String key;
	private final String name;
	private final String description;
	private final Set<ConfigurationElement> configurationElements;
	private final Map<String, ConfigurationElement> configurationElementMap;

	public DefaultProviderMetamodel(String key, String name, String description, ConfigurationElement... configurationElements) {
		this(key, name, description, new LinkedHashSet<>(Arrays.asList(configurationElements)));
	}

	public DefaultProviderMetamodel(String scheme, String name, String description, Set<ConfigurationElement> configurationElements) {
		this.key = scheme;
		this.name = name;
		this.description = description;
		Set<ConfigurationElement> set = new LinkedHashSet<>();
		Map<String, ConfigurationElement> map = new HashMap<>(configurationElements.size());
		
		for (ConfigurationElement element : configurationElements) {
			set.add(element);
			map.put(element.getKey(), element);
		}

		this.configurationElements = Collections.unmodifiableSet(set);
		this.configurationElementMap = Collections.unmodifiableMap(map);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public ConfigurationElement getConfigurationElement(String key) {
		return configurationElementMap.get(key);
	}

	@Override
	public Set<ConfigurationElement> getConfigurationElements() {
		return configurationElements;
	}

}
