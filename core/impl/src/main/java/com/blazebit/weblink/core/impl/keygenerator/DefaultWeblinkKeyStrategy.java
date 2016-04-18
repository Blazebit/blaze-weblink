package com.blazebit.weblink.core.impl.keygenerator;

import javax.inject.Inject;

import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategy;
import com.blazebit.weblink.core.model.jpa.Weblink;

public class DefaultWeblinkKeyStrategy implements WeblinkKeyStrategy {

	@Inject
	private WeblinkGroupSequenceService weblinkGroupSequenceService;
	
	@Override
	public String generateKey(Weblink weblink) {
		return weblinkGroupSequenceService.getNextCode(weblink.getId().getWeblinkGroupId());
	}

}
