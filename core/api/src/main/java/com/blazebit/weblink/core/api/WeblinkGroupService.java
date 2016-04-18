package com.blazebit.weblink.core.api;

import com.blazebit.weblink.core.model.jpa.WeblinkGroup;

public interface WeblinkGroupService {

	public void put(WeblinkGroup bucket);

	public void delete(String bucketId);
}
