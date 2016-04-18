package com.blazebit.weblink.core.api;

import com.blazebit.persistence.QueryBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkId;

public interface WeblinkDataAccess {

	public Weblink findById(WeblinkId bucketObjectId);

	public <T> T findById(WeblinkId bucketObjectId, EntityViewSetting<T, ? extends QueryBuilder<T,?>> setting);
	
	public <T> T findByIdForDispatch(WeblinkId weblinkId, EntityViewSetting<T, ? extends QueryBuilder<T,?>> setting);
}
