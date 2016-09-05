package com.blazebit.weblink.core.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;

import com.blazebit.weblink.core.api.WeblinkDispatcherFactoryDataAccess;
import com.blazebit.weblink.core.api.WeblinkGroupNotFoundException;
import com.blazebit.weblink.core.api.WeblinkKeyStrategyFactoryDataAccess;
import com.blazebit.weblink.core.api.WeblinkNotFoundException;
import com.blazebit.weblink.core.api.WeblinkService;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcherFactory;
import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategy;
import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategyFactory;
import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkGroup;
import com.blazebit.weblink.core.model.jpa.WeblinkId;
import com.blazebit.weblink.core.model.jpa.WeblinkSecurityGroup;

@Stateless
public class WeblinkServiceImpl extends AbstractService implements WeblinkService {

	@Inject
	private WeblinkKeyStrategyFactoryDataAccess weblinkKeyStrategyFactoryDataAccess;
	@Inject
	private WeblinkDispatcherFactoryDataAccess weblinkDispatcherFactoryDataAccess;

	@Override
	public void put(Weblink weblink) {
		if (em.contains(weblink)) {
			em.detach(weblink);
		}
		
		List<Weblink> results = cbf.create(em, Weblink.class)
			.fetch("weblinkGroup")
			.where("id.weblinkGroupId").eq(weblink.getId().getWeblinkGroupId())
			.where("id.name").eq(weblink.getId().getName())
			.getQuery()
			.setLockMode(LockModeType.PESSIMISTIC_WRITE)
			.getResultList();

		// Fallback to create
		if (results.isEmpty()) {
			createObject(weblink);
			return;
		}
		
		// TODO: check access rights?
		
		// TODO: allow weblink group update?
		
		Weblink currentWeblink = results.get(0); 
		
		currentWeblink.setExpirationTime(weblink.getExpirationTime());
		currentWeblink.setTags(weblink.getTags());
		currentWeblink.setTargetUri(weblink.getTargetUri());
		// TODO: do some checks
		currentWeblink.setWeblinkSecurityGroup(em.getReference(WeblinkSecurityGroup.class, weblink.getWeblinkSecurityGroup().getId()));

		if (!currentWeblink.getDispatcherType().equals(weblink.getDispatcherType())
				|| !currentWeblink.getDispatcherConfiguration().equals(weblink.getDispatcherConfiguration())) {
			// check if dispatcher is valid and can be built
			WeblinkDispatcherFactory dispatcherFactory = weblinkDispatcherFactoryDataAccess.findByKey(weblink.getDispatcherType());
			dispatcherFactory.createWeblinkDispatcher(weblink.getDispatcherConfiguration());
			
			currentWeblink.setDispatcherType(weblink.getDispatcherType());
			currentWeblink.setDispatcherConfiguration(weblink.getDispatcherConfiguration());
		}
		
		em.merge(currentWeblink);
		em.flush();
	}

	@Override
	public String create(Weblink weblink) {
		if (weblink.getId().getName() != null) {
			throw new IllegalArgumentException("The given weblink already has a name set! " + weblink.getId().getName());
		}

		if (em.contains(weblink)) {
			em.detach(weblink);
		}
		
		createObject(weblink);
		return weblink.getId().getName();
	}

	private void createObject(Weblink weblink) {
		List<WeblinkGroup> results = cbf.create(em, WeblinkGroup.class)
				.where("id").eq(weblink.getId().getWeblinkGroupId())
				.getQuery()
				.setLockMode(LockModeType.PESSIMISTIC_WRITE)
				.getResultList();
		
		if (results.isEmpty()) {
			throw new WeblinkGroupNotFoundException("Weblink Group not found!");
		}
		
		WeblinkGroup weblinkGroup = results.get(0);
		weblink.setWeblinkGroup(weblinkGroup);
		
		if (weblink.getId().getName() == null) {
			// Create a weblink name
			WeblinkKeyStrategyFactory keyStrategyFactory = weblinkKeyStrategyFactoryDataAccess.findByKey(weblinkGroup.getKeyStrategyType());
			WeblinkKeyStrategy keyStrategy = keyStrategyFactory.createWeblinkKeyStrategy(weblinkGroup.getKeyStrategyConfiguration());
			weblink.getId().setName(keyStrategy.generateKey(weblink));
		}

		// check if dispatcher is valid and can be built
		WeblinkDispatcherFactory dispatcherFactory = weblinkDispatcherFactoryDataAccess.findByKey(weblink.getDispatcherType());
		dispatcherFactory.createWeblinkDispatcher(weblink.getDispatcherConfiguration());

		weblink.setWeblinkSecurityGroup(em.getReference(WeblinkSecurityGroup.class, weblink.getWeblinkSecurityGroup().getId()));

		em.persist(weblink);
		em.flush();
	}

	@Override
	public void delete(WeblinkId weblinkId) {
		try {
			em.remove(em.getReference(Weblink.class, weblinkId));
			em.flush();
		} catch (PersistenceException ex) {
			throw new WeblinkNotFoundException("Weblink [" + weblinkId + "] does not exist!");
		}
		
		// TODO: don't know what to do with that
//		bucketObjectDeleted.fire(new BucketObjectDeletedEvent(new BucketObjectId(new Bucket(bucketId), objectName)));
	}

}
