package com.blazebit.weblink.core.api.spi;

import java.util.Map;

public interface WeblinkMatcherFactory {
	
	public ProviderMetamodel getMetamodel();

	public WeblinkMatcher createWeblinkMatcher(Map<String, ? extends Object> properties);
	
}
