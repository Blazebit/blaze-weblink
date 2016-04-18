package com.blazebit.weblink.core.api;

import java.util.List;
import java.util.Map;

import com.blazebit.weblink.core.api.spi.WeblinkMatcherFactory;

public interface WeblinkMatcherFactoryDataAccess {

	public String getType(Map<String, String> configuration);

	public List<WeblinkMatcherFactory> findAll();

	public WeblinkMatcherFactory findByKey(String scheme);
}
