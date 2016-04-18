package com.blazebit.weblink.core.api;

import java.util.List;
import java.util.Map;

import com.blazebit.weblink.core.api.spi.WeblinkDispatcherFactory;

public interface WeblinkDispatcherFactoryDataAccess {

	public String getType(Map<String, String> configuration);

	public List<WeblinkDispatcherFactory> findAll();

	public WeblinkDispatcherFactory findByKey(String scheme);
}
