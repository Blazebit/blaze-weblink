package com.blazebit.weblink.core.api;

import com.blazebit.persistence.QueryBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraint;
import com.blazebit.weblink.core.model.jpa.Account;
import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkId;
import com.blazebit.weblink.core.model.jpa.WeblinkSecurityGroup;

import java.util.List;
import java.util.Map;

public interface WeblinkSecurityGroupDataAccess {

	public <T> T findByOwnerAndName(Account account, String name, EntityViewSetting<T, ? extends QueryBuilder<T, ?>> setting);
	
	public <T> List<T> findAllByAccountId(Long accountId, EntityViewSetting<T, ? extends QueryBuilder<T, ?>> setting);

	public List<WeblinkSecurityConstraint> getSecurityGroupConstraints(Long securityGroupId, List<Map<String, String>> securityGroupConfiguration);
}
