package com.blazebit.weblink.core.impl;

import com.blazebit.weblink.core.api.*;
import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategyFactory;
import com.blazebit.weblink.core.api.spi.WeblinkMatcherFactory;
import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraintFactory;
import com.blazebit.weblink.core.model.jpa.Account;
import com.blazebit.weblink.core.model.jpa.WeblinkGroup;
import com.blazebit.weblink.core.model.jpa.WeblinkGroupSequence;
import com.blazebit.weblink.core.model.jpa.WeblinkSecurityGroup;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Map;

@Stateless
public class WeblinkSecurityGroupServiceImpl extends AbstractService implements WeblinkSecurityGroupService {

	@Inject
	private WeblinkSecurityConstraintFactoryDataAccess securityConstraintFactoryDataAccess;

	@Override
	public void put(WeblinkSecurityGroup securityGroup) {
		checkConstraintConfiguration(securityGroup.getConstraintConfigurations());

		if (em.contains(securityGroup)) {
			em.detach(securityGroup);
		}

		try {
			WeblinkSecurityGroup currentSecurityGroup = cbf.create(em, WeblinkSecurityGroup.class)
				.where("name").eq(securityGroup.getName())
				.where("owner.id").eq(securityGroup.getOwner().getId())
				.getQuery()
				.setLockMode(LockModeType.PESSIMISTIC_WRITE)
				.getSingleResult();

			currentSecurityGroup.setName(securityGroup.getName());
			currentSecurityGroup.setTags(securityGroup.getTags());
			currentSecurityGroup.setConstraintConfigurations(securityGroup.getConstraintConfigurations());

			em.flush();
		} catch (NoResultException ex) {
			em.persist(securityGroup);
			return;
		}
	}

	@Override
	public void delete(Account account, String securityGroupName) {
		try {
			WeblinkSecurityGroup securityGroup = cbf.create(em, WeblinkSecurityGroup.class)
				.where("name").eq(securityGroupName)
				.where("owner.id").eq(account.getId())
				.getQuery()
				.setLockMode(LockModeType.PESSIMISTIC_WRITE)
				.getSingleResult();
			em.remove(securityGroup);
			em.flush();
		} catch (NoResultException ex) {
			throw new WeblinkSecurityGroupNotFoundException("WeblinkSecurityGroup '" + securityGroupName + "' does not exist!");
		} catch (PersistenceException ex) {
			// TODO: delete all references to the security group or mark this as deleted?
			throw new WeblinkException("Can not delete weblink security group!", ex);
		}
	}

	private void checkConstraintConfiguration(List<Map<String, String>> securityGroupConfiguration) {
		if (securityGroupConfiguration == null || securityGroupConfiguration.isEmpty()) {
			throw new IllegalArgumentException("Invalid empty constraint configurations!");
		}
		for (Map<String, String> constraintConfig : securityGroupConfiguration) {
			WeblinkSecurityConstraintFactory factory = securityConstraintFactoryDataAccess.findByKey(securityConstraintFactoryDataAccess.getType(constraintConfig));
			if (factory == null) {
				throw new IllegalArgumentException("Unexpected constraint configuration without a type!");
			}

			factory.createConstraint(constraintConfig);
		}
	}

}
