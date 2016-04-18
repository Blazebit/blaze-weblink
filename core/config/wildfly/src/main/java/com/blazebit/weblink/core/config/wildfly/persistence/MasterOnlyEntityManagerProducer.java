package com.blazebit.weblink.core.config.wildfly.persistence;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import com.blazebit.weblink.core.config.api.persistence.MasterOnly;

public class MasterOnlyEntityManagerProducer {

	@Produces
	@MasterOnly
	@PersistenceUnit(unitName = "WeblinkMasterOnly")
	private EntityManagerFactory emf;
	
	@Produces
	@MasterOnly
	@PersistenceContext(unitName = "WeblinkMasterOnly")
	private EntityManager em;
	
}
