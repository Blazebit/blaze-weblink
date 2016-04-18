package com.blazebit.weblink.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.blazebit.weblink.core.api.WeblinkDispatcherFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcherFactory;

@Singleton
public class WeblinkDispatcherFactoryDataAccessImpl extends AbstractDataAccess implements WeblinkDispatcherFactoryDataAccess {

	private final List<WeblinkDispatcherFactory> list;
	private final Map<String, WeblinkDispatcherFactory> map;
	
	@Inject
	public WeblinkDispatcherFactoryDataAccessImpl(Instance<WeblinkDispatcherFactory> weblinkDispatcherFactories) {
		List<WeblinkDispatcherFactory> list = new ArrayList<>();
		Map<String, WeblinkDispatcherFactory> map = new HashMap<>();
		
		for (WeblinkDispatcherFactory factory : weblinkDispatcherFactories) {
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
	public List<WeblinkDispatcherFactory> findAll() {
		return list;
	}

	@Override
	public WeblinkDispatcherFactory findByKey(String key) {
		return map.get(key);
	}

}
