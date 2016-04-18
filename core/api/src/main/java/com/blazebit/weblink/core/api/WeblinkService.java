package com.blazebit.weblink.core.api;

import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkId;

public interface WeblinkService {

	public String create(Weblink weblink);

	public void put(Weblink weblink);
	
	public void delete(WeblinkId weblinkId);
	
}
