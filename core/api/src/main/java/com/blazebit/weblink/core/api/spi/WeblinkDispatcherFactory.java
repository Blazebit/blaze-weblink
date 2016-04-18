package com.blazebit.weblink.core.api.spi;

import java.util.Map;

public interface WeblinkDispatcherFactory {
	
	public ProviderMetamodel getMetamodel();

	public WeblinkDispatcher createWeblinkDispatcher(Map<String, ? extends Object> properties);
	
}
