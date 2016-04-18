package com.blazebit.weblink.core.api.spi;

import java.util.Set;

public interface ProviderMetamodel {
	
	public String getKey();
	
	public String getName();
	
	public String getDescription();
	
	public ConfigurationElement getConfigurationElement(String key);
	
	public Set<ConfigurationElement> getConfigurationElements();

}
