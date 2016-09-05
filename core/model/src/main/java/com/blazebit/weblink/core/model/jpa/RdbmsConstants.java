package com.blazebit.weblink.core.model.jpa;

public interface RdbmsConstants {

	public static final String PREFIX = "wblk_"; 
	public static final int NAME_MAX_LENGTH = 255;
	public static final int PROVIDER_NAME_MAX_LENGTH = 255;
	public static final int URI_MAX_LENGTH = 2000; // 2000 chars for a url should be enough
	
	public static final String CONFIGURATION_COLUMN_DEFINITION = "CLOB NOT NULL";
}
