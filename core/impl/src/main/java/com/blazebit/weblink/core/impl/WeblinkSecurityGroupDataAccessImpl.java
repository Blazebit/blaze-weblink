package com.blazebit.weblink.core.impl;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.QueryBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.WeblinkSecurityConstraintFactoryDataAccess;
import com.blazebit.weblink.core.api.WeblinkSecurityGroupDataAccess;
import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraint;
import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraintFactory;
import com.blazebit.weblink.core.model.jpa.Account;
import com.blazebit.weblink.core.model.jpa.WeblinkSecurityGroup;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Stateless
public class WeblinkSecurityGroupDataAccessImpl extends AbstractDataAccess implements WeblinkSecurityGroupDataAccess {

	@Inject
	private WeblinkSecurityConstraintFactoryDataAccess securityConstraintFactoryDataAccess;

	@Override
	public <T> T findByOwnerAndName(Account account, String securityGroupName, EntityViewSetting<T, ? extends QueryBuilder<T, ?>> setting) {
		if (account == null) {
			return null;
		}
		try {
			CriteriaBuilder<WeblinkSecurityGroup> cb =  cbf.create(em, WeblinkSecurityGroup.class)
					.where("name").eq(securityGroupName)
					.where("owner.id").eq(account.getId());
			return evm.applySetting(setting, cb).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public <T> List<T> findAllByAccountId(Long accountId, EntityViewSetting<T, ? extends QueryBuilder<T, ?>> setting) {
		CriteriaBuilder<WeblinkSecurityGroup> cb =  cbf.create(em, WeblinkSecurityGroup.class)
				.where("owner.id").eq(accountId);
		return evm.applySetting(setting, cb).getResultList();
	}

	@Override
	public List<WeblinkSecurityConstraint> getSecurityGroupConstraints(Long securityGroupId, List<Map<String, String>> securityGroupConfiguration) {
		// TODO: apply caching
//		if (cache.contains(securityGroupId)) {
//			return cache.get(securityGroupId);
//		}
		List<WeblinkSecurityConstraint> result = new ArrayList<>(securityGroupConfiguration.size());
		for (Map<String, String> constraintConfig : securityGroupConfiguration) {
			WeblinkSecurityConstraintFactory factory = securityConstraintFactoryDataAccess.findByKey(securityConstraintFactoryDataAccess.getType(constraintConfig));
			result.add(factory.createConstraint(constraintConfig));
		}

		return result;
	}

}
