package com.blazebit.weblink.testsuite.common.persistence;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import com.blazebit.weblink.core.config.api.persistence.MasterOnly;

@Singleton
@Startup
public class EntityViewManagerMasterOnlyProducer {

	// inject the configuration provided by the cdi integration
	@Inject
	private EntityViewConfiguration config;
	@Inject
	private Event<EntityViewConfiguration> configEvent;

	// inject the criteria builder factory which will be used along with the entity view manager
	@Inject
	@MasterOnly
	private CriteriaBuilderFactory criteriaBuilderFactory;

	private EntityViewManager evm;
	
	@PostConstruct
	public void init() {
    	configEvent.fire(config);
    	evm = config.createEntityViewManager(criteriaBuilderFactory);
	}

    @Produces
    @MasterOnly
    @ApplicationScoped
    public EntityViewManager createEntityViewManager() {
    	return evm;
    }
}
