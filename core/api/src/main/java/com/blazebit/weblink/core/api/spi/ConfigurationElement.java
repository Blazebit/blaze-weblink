package com.blazebit.weblink.core.api.spi;

public interface ConfigurationElement {

	public String getKey();
	
	public String getType();

	public String getDefaultValue();
	
	public String getName();
	
	public String getDescription();
	
}
