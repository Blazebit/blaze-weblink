package com.blazebit.weblink.core.api.spi;

import com.blazebit.weblink.core.model.jpa.Weblink;


public interface WeblinkKeyStrategy {
	
	public String generateKey(Weblink weblink);
	
}
