package com.blazebit.weblink.core.api;

import com.blazebit.weblink.core.model.jpa.Account;
import com.blazebit.weblink.core.model.jpa.WeblinkSecurityGroup;

public interface WeblinkSecurityGroupService {

	public void put(WeblinkSecurityGroup securityGroup);

	public void delete(Account account, String securityGroupName);
	
}
