package com.blazebit.weblink.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.blazebit.weblink.core.api.WeblinkMatcherFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkMatcherFactory;

@Singleton
public class WeblinkMatcherFactoryDataAccessImpl extends AbstractDataAccess implements WeblinkMatcherFactoryDataAccess {

	private final List<WeblinkMatcherFactory> list;
	private final Map<String, WeblinkMatcherFactory> map;
	
	@Inject
	public WeblinkMatcherFactoryDataAccessImpl(Instance<WeblinkMatcherFactory> weblinkMatcherFactories) {
		List<WeblinkMatcherFactory> list = new ArrayList<>();
		Map<String, WeblinkMatcherFactory> map = new HashMap<>();
		
		for (WeblinkMatcherFactory factory : weblinkMatcherFactories) {
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
	public List<WeblinkMatcherFactory> findAll() {
		return list;
	}

	@Override
	public WeblinkMatcherFactory findByKey(String key) {
		return map.get(key);
	}

}
