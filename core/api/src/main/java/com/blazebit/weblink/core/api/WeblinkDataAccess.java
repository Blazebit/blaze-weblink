package com.blazebit.weblink.core.api;

import java.util.List;

import com.blazebit.persistence.QueryBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkId;

public interface WeblinkDataAccess {

	public Weblink findById(WeblinkId weblinkId);

	public <T> T findById(WeblinkId weblinkId, EntityViewSetting<T, ? extends QueryBuilder<T,?>> setting);
	
	public <T> T findByIdForDispatch(WeblinkId weblinkId, EntityViewSetting<T, ? extends QueryBuilder<T,?>> setting);
	
	public <T> List<T> findByIds(List<WeblinkId> weblinkIds, EntityViewSetting<T, ? extends QueryBuilder<T,?>> setting);
	
	public <T> List<T> findAllByWeblinkGroup(String weblinkGroupId, EntityViewSetting<T, ? extends QueryBuilder<T, ?>> setting);
}
