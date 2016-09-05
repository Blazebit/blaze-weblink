package com.blazebit.weblink.core.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.QueryBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.WeblinkGroupDataAccess;
import com.blazebit.weblink.core.model.jpa.WeblinkGroup;

@Stateless
public class WeblinkGroupDataAccessImpl extends AbstractDataAccess implements WeblinkGroupDataAccess {

	@Override
	public WeblinkGroup findByName(String weblinkGroupName) {
		try {
			return cbf.create(em, WeblinkGroup.class)
					.where("id").eq(weblinkGroupName)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	@Override
	public <T> T findByName(String weblinkGroupName, EntityViewSetting<T, ? extends QueryBuilder<T,?>> setting) {
		try {
			CriteriaBuilder<WeblinkGroup> cb =  cbf.create(em, WeblinkGroup.class)
					.where("id").eq(weblinkGroupName);
			return evm.applySetting(setting, cb).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	@Override
	public <T> T findByName(String weblinkGroupName, String prefix, Integer limit, String marker, EntityViewSetting<T, ? extends QueryBuilder<T,?>> setting) {
		if (limit == null) {
			limit = 1000;
		} else if (limit > 1000) {
			throw new IllegalArgumentException("Limit may not exceed 1000!");
		}
		
		try {
			CriteriaBuilder<WeblinkGroup> cb =  cbf.create(em, WeblinkGroup.class)
					.where("id").eq(weblinkGroupName);
			
			if (prefix != null && !prefix.isEmpty()) {
				cb.where("links.id.name").like().value(prefix.replaceAll("%", "\\%") + "%").escape('\\');
			}
			
			if (marker != null && !marker.isEmpty()) {
				cb.where("links.id.name").gt(marker);
			}
			
			// TODO: implement constraint and marker in query and also for passing into entity views
			setting.addOptionalParameter("prefix", prefix);
			setting.addOptionalParameter("constraint", limit);
			setting.addOptionalParameter("marker", marker);
			cb.setMaxResults(limit);
			return evm.applySetting(setting, cb).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public <T> List<T> findByAccountId(long accountId, EntityViewSetting<T, ? extends QueryBuilder<T,?>> setting) {
		CriteriaBuilder<WeblinkGroup> cb = cbf.create(em, WeblinkGroup.class)
				.where("owner.id").eq(accountId);
		return evm.applySetting(setting, cb).getResultList();
	}

	@Override
	public <T> List<T> findAll(EntityViewSetting<T, ? extends QueryBuilder<T, ?>> setting) {
		CriteriaBuilder<WeblinkGroup> cb = cbf.create(em, WeblinkGroup.class);
		return evm.applySetting(setting, cb).getResultList();
	}

}
