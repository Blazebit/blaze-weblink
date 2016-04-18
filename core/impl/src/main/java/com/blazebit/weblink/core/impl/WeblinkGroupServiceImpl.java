package com.blazebit.weblink.core.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;

import com.blazebit.weblink.core.api.AccountNotFoundException;
import com.blazebit.weblink.core.api.WeblinkGroupNotEmptyException;
import com.blazebit.weblink.core.api.WeblinkGroupNotFoundException;
import com.blazebit.weblink.core.api.WeblinkGroupService;
import com.blazebit.weblink.core.api.WeblinkKeyStrategyFactoryDataAccess;
import com.blazebit.weblink.core.api.WeblinkMatcherFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategyFactory;
import com.blazebit.weblink.core.api.spi.WeblinkMatcherFactory;
import com.blazebit.weblink.core.model.jpa.Account;
import com.blazebit.weblink.core.model.jpa.WeblinkGroup;
import com.blazebit.weblink.core.model.jpa.WeblinkGroupSequence;

@Stateless
public class WeblinkGroupServiceImpl extends AbstractService implements WeblinkGroupService {

	@Inject
	private WeblinkKeyStrategyFactoryDataAccess weblinkKeyStrategyFactoryDataAccess;
	@Inject
	private WeblinkMatcherFactoryDataAccess weblinkMatcherFactoryDataAccess;
	
	@Override
	public void put(WeblinkGroup weblinkGroup) {
		if (em.contains(weblinkGroup)) {
			em.detach(weblinkGroup);
		}
		
		WeblinkGroup currentWeblinkGroup = em.find(WeblinkGroup.class, weblinkGroup.getId(), LockModeType.PESSIMISTIC_WRITE);
		
		// Fallback to create
		if (currentWeblinkGroup == null) {
			createObject(weblinkGroup);
			return;
		}

		// TODO: check access rights?
		
		// TODO: allow owner update?
//		currentWeblinkGroup.setOwner(weblinkGroup.getOwner());

		// TODO: changing the key strategy should actually not be allowed
		if (!currentWeblinkGroup.getKeyStrategyType().equals(weblinkGroup.getKeyStrategyType())
				|| !currentWeblinkGroup.getKeyStrategyConfiguration().equals(weblinkGroup.getKeyStrategyConfiguration())) {
			// check if key strategy is valid and can be built
			WeblinkKeyStrategyFactory keyStrategyFactory = weblinkKeyStrategyFactoryDataAccess.findByKey(weblinkGroup.getKeyStrategyType());
			keyStrategyFactory.createWeblinkKeyStrategy(weblinkGroup.getKeyStrategyConfiguration());
			
			currentWeblinkGroup.setKeyStrategyType(weblinkGroup.getKeyStrategyType());
			currentWeblinkGroup.setKeyStrategyConfiguration(weblinkGroup.getKeyStrategyConfiguration());
		}

		if (!currentWeblinkGroup.getMatcherType().equals(weblinkGroup.getMatcherType())
				|| !currentWeblinkGroup.getMatcherConfiguration().equals(weblinkGroup.getMatcherConfiguration())) {
			// check if matcher is valid and can be built
			WeblinkMatcherFactory matcherFactory = weblinkMatcherFactoryDataAccess.findByKey(weblinkGroup.getMatcherType());
			matcherFactory.createWeblinkMatcher(weblinkGroup.getMatcherConfiguration());

			currentWeblinkGroup.setMatcherType(weblinkGroup.getMatcherType());
			currentWeblinkGroup.setKeyStrategyConfiguration(weblinkGroup.getMatcherConfiguration());
		}
		
		em.merge(currentWeblinkGroup);
		em.flush();
	}

	private void createObject(WeblinkGroup weblinkGroup) {
		Account owner = em.find(Account.class, weblinkGroup.getOwner().getId());
		
		if (owner == null) {
			throw new AccountNotFoundException("Account not found!");
		}
		
		weblinkGroup.setOwner(owner);

		// check if key strategy is valid and can be built
		WeblinkKeyStrategyFactory keyStrategyFactory = weblinkKeyStrategyFactoryDataAccess.findByKey(weblinkGroup.getKeyStrategyType());
		keyStrategyFactory.createWeblinkKeyStrategy(weblinkGroup.getKeyStrategyConfiguration());

		// check if matcher is valid and can be built
		WeblinkMatcherFactory matcherFactory = weblinkMatcherFactoryDataAccess.findByKey(weblinkGroup.getMatcherType());
		matcherFactory.createWeblinkMatcher(weblinkGroup.getMatcherConfiguration());
		
		
		WeblinkGroupSequence sequence = new WeblinkGroupSequence();
		sequence.setId(weblinkGroup.getId());
		sequence.setWeblinkGroup(weblinkGroup);

		em.persist(weblinkGroup);
		em.persist(sequence);
		em.flush();
	}

	@Override
	public void delete(String weblinkGroupId) {
		WeblinkGroup weblinkGroup = em.find(WeblinkGroup.class, weblinkGroupId, LockModeType.PESSIMISTIC_WRITE);
		
		// TODO: check access rights?
		
		if (weblinkGroup == null) {
			throw new WeblinkGroupNotFoundException("WeblinkGroup '" + weblinkGroupId + "' does not exist!");
		}
		
		try {
			em.remove(weblinkGroup);
			em.flush();
		} catch (PersistenceException ex) {
			throw new WeblinkGroupNotEmptyException("Can not delete not empty weblink group!", ex);
		}
//		weblinkGroupDeleted.fire(new WeblinkGroupDeletedEvent(weblinkGroupId));
	}

}
