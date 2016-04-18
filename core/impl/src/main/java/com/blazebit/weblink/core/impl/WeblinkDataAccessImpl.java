package com.blazebit.weblink.core.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.QueryBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.WeblinkDataAccess;
import com.blazebit.weblink.core.api.WeblinkDispatcherFactoryDataAccess;
import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkId;

@Stateless
public class WeblinkDataAccessImpl extends AbstractDataAccess implements WeblinkDataAccess {
	
	@Inject
	private WeblinkDispatcherFactoryDataAccess weblinkDispatcherFactoryDataAccess;
	
	@Override
	public Weblink findById(WeblinkId weblinkId) {
		try {
			return cbf.create(em, Weblink.class)
					.where("id").eq(weblinkId)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public <T> T findById(WeblinkId weblinkId, EntityViewSetting<T, ? extends QueryBuilder<T,?>> setting) {
		try {
			CriteriaBuilder<Weblink> cb = cbf.create(em, Weblink.class)
					.where("id").eq(weblinkId);
			return evm.applySetting(setting, cb).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public <T> T findByIdForDispatch(WeblinkId weblinkId, EntityViewSetting<T, ? extends QueryBuilder<T,?>> setting) {
		try {
			CriteriaBuilder<Weblink> cb = cbf.create(em, Weblink.class)
					.where("id").eq(weblinkId)
					.where("expirationTime").gtExpression("CURRENT_TIMESTAMP");
			return evm.applySetting(setting, cb).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

}
