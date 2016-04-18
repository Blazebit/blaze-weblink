package com.blazebit.weblink.core.impl.keygenerator;

import java.util.UUID;

import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategy;
import com.blazebit.weblink.core.model.jpa.Weblink;

public class UuidWeblinkKeyStrategy implements WeblinkKeyStrategy {
	
	@Override
	public String generateKey(Weblink weblink) {
		return UUID.randomUUID().toString();
	}

}
