package com.blazebit.weblink.core.api.spi;

import java.util.Map;

public interface WeblinkKeyStrategyFactory {
	
	public ProviderMetamodel getMetamodel();

	public WeblinkKeyStrategy createWeblinkKeyStrategy(Map<String, ? extends Object> properties);
	
}
