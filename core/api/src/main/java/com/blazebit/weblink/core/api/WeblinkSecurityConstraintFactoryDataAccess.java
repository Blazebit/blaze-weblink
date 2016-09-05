package com.blazebit.weblink.core.api;

import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraintFactory;

import java.util.List;
import java.util.Map;

public interface WeblinkSecurityConstraintFactoryDataAccess {

	public String getType(Map<String, String> configuration);

	public List<WeblinkSecurityConstraintFactory> findAll();

	public WeblinkSecurityConstraintFactory findByKey(String type);
}
