package com.blazebit.weblink.core.api.spi;

import java.util.Map;

public interface WeblinkSecurityConstraintFactory {
	
	public ProviderMetamodel getMetamodel();

	public WeblinkSecurityConstraint createConstraint(Map<String, ? extends Object> properties);
	
}
