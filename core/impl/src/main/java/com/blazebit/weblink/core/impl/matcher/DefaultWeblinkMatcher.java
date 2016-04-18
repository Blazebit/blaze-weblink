package com.blazebit.weblink.core.impl.matcher;

import com.blazebit.weblink.core.api.spi.WeblinkMatcher;
import com.blazebit.weblink.core.model.jpa.WeblinkGroup;

public class DefaultWeblinkMatcher implements WeblinkMatcher {

	@Override
	public boolean matches(WeblinkGroup weblinkGroup) {
		// TODO: implement support for multiple weblink groups
		return true;
	}

}
