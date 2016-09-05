package com.blazebit.weblink.core.impl.keygenerator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.persistence.LockModeType;

import com.blazebit.persistence.ReturningResult;
import com.blazebit.weblink.core.api.WeblinkException;
import com.blazebit.weblink.core.impl.AbstractService;
import com.blazebit.weblink.core.model.jpa.WeblinkGroupSequence;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class WeblinkGroupSequenceService extends AbstractService {
	
	private final ConcurrentMap<String, StringCodeGenerator> stringCodeGenerators = new ConcurrentHashMap<>();

	private StringCodeGenerator getStringCodeGenerator(String weblinkGroupId) {
		StringCodeGenerator stringCodeGenerator = stringCodeGenerators.get(weblinkGroupId);
		
		if (stringCodeGenerator == null) {
			stringCodeGenerator = new StringCodeGenerator(weblinkGroupId, 5, "abcdefghijklmnopqrstuvwxyz1234567890");
			stringCodeGenerators.putIfAbsent(weblinkGroupId, stringCodeGenerator);
		}

		return stringCodeGenerator;
	}
	
	public String getNextCode(String weblinkGroupId) {
		// NOTE: We only do an update here, because we ensure that the weblink group sequence is created at creation time of the weblink group
//		ReturningResult<Long> result = cbf.update(em, WeblinkGroupSequence.class)
//			.setExpression("value", "value + 1")
//			.where("id").eq(weblinkGroupId)
//			.executeWithReturning("value", Long.class);
//
//		if (result.getUpdateCount() != 1) {
//			throw new WeblinkException("Updating the weblink group sequence of '" + weblinkGroupId + "' did not result in an update count of 1!");
//		}
		WeblinkGroupSequence seq = cbf.create(em, WeblinkGroupSequence.class)
				.where("id").eq(weblinkGroupId)
				.getQuery()
				.setLockMode(LockModeType.PESSIMISTIC_WRITE)
				.getSingleResult();

		Long newValue = seq.getValue() + 1;
		seq.setValue(newValue);
		em.flush();

		String code = getStringCodeGenerator(weblinkGroupId).encode(newValue);
		
		return code;
	}
}
