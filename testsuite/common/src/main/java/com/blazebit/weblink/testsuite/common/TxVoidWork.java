package com.blazebit.weblink.testsuite.common;

import javax.persistence.EntityManager;

public interface TxVoidWork {

	public void doWork(EntityManager em);
	
}
