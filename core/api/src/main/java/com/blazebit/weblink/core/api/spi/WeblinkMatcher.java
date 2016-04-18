package com.blazebit.weblink.core.api.spi;

import com.blazebit.weblink.core.model.jpa.WeblinkGroup;


public interface WeblinkMatcher {
	
	public boolean matches(WeblinkGroup weblinkGroup);
	
}
