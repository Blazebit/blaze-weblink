package com.blazebit.weblink.testsuite.common.persistence;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.hibernate.Session;

import com.blazebit.weblink.core.config.api.persistence.ReadOnly;


public class ReadOnlyEntityManagerProducer {

	@Produces
	@ReadOnly
	@PersistenceUnit(unitName = PersistenceUnits.WEBLINK_TEST_READ_ONLY)
	private EntityManagerFactory emf;
	
	@PersistenceContext(unitName = PersistenceUnits.WEBLINK_TEST_READ_ONLY)
	private EntityManager em;

	@Produces
	@ReadOnly
	@RequestScoped
	public EntityManager create() {
		// Make the underlying session read-only
		em.unwrap(Session.class).setDefaultReadOnly(true);
		return em;
	}
}