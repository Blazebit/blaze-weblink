package com.blazebit.weblink.core.impl;

import com.blazebit.weblink.core.api.WeblinkDispatcherFactoryDataAccess;
import com.blazebit.weblink.core.api.WeblinkSecurityConstraintFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcherFactory;
import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraintFactory;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class WeblinkSecurityConstraintFactoryDataAccessImpl extends AbstractDataAccess implements WeblinkSecurityConstraintFactoryDataAccess {

	private final List<WeblinkSecurityConstraintFactory> list;
	private final Map<String, WeblinkSecurityConstraintFactory> map;

	@Inject
	public WeblinkSecurityConstraintFactoryDataAccessImpl(Instance<WeblinkSecurityConstraintFactory> weblinkSecurityConstraintFactories) {
		List<WeblinkSecurityConstraintFactory> list = new ArrayList<>();
		Map<String, WeblinkSecurityConstraintFactory> map = new HashMap<>();
		
		for (WeblinkSecurityConstraintFactory factory : weblinkSecurityConstraintFactories) {
			ProviderMetamodel metamodel = factory.getMetamodel();
			list.add(factory);
			map.put(metamodel.getKey(), factory);
		}
		
		this.list = Collections.unmodifiableList(list);
		this.map = Collections.unmodifiableMap(map);
	}

	@Override
	public String getType(Map<String, String> configuration) {
		String type = null;
		
		if (configuration != null) {
			type = configuration.get("type");
		}
		
		return type;
	}

	@Override
	public List<WeblinkSecurityConstraintFactory> findAll() {
		return list;
	}

	@Override
	public WeblinkSecurityConstraintFactory findByKey(String key) {
		return map.get(key);
	}

}
