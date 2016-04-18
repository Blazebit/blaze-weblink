package com.blazebit.weblink.testsuite.common.cache;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import com.blazebit.weblink.core.config.api.cache.LocalCache;

public class LocalCacheManagerProducer {
	
	@Produces
	@LocalCache
	@ApplicationScoped
	CacheManager produceCacheManager() {
		return Caching.getCachingProvider().getCacheManager();
	}
    
    public void dispose(@Disposes @LocalCache CacheManager cacheManager) {
    	cacheManager.close();
    }
	
}