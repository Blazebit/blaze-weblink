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
import com.blazebit.weblink.core.api.WeblinkKeyStrategyFactoryDataAccess;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcherFactory;
import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategyFactory;

@Singleton
public class WeblinkKeyStrategyFactoryDataAccessImpl extends AbstractDataAccess implements WeblinkKeyStrategyFactoryDataAccess {

	private final List<WeblinkKeyStrategyFactory> list;
	private final Map<String, WeblinkKeyStrategyFactory> map;
	
	@Inject
	public WeblinkKeyStrategyFactoryDataAccessImpl(Instance<WeblinkKeyStrategyFactory> weblinkKeyStrategyFactories) {
		List<WeblinkKeyStrategyFactory> list = new ArrayList<>();
		Map<String, WeblinkKeyStrategyFactory> map = new HashMap<>();
		
		for (WeblinkKeyStrategyFactory factory : weblinkKeyStrategyFactories) {
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
	public List<WeblinkKeyStrategyFactory> findAll() {
		return list;
	}

	@Override
	public WeblinkKeyStrategyFactory findByKey(String key) {
		return map.get(key);
	}

}
