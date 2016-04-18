package com.blazebit.weblink.core.api;

import java.util.List;
import java.util.Map;

import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategyFactory;

public interface WeblinkKeyStrategyFactoryDataAccess {

	public String getType(Map<String, String> configuration);

	public List<WeblinkKeyStrategyFactory> findAll();

	public WeblinkKeyStrategyFactory findByKey(String key);
}
