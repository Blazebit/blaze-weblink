package com.blazebit.weblink.rest.impl.view;

import java.util.Calendar;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.weblink.core.model.jpa.WeblinkGroup;
import com.blazebit.weblink.rest.model.WeblinkGroupListElementRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@EntityView(WeblinkGroup.class)
public abstract class WeblinkGroupListElementRepresentationView extends WeblinkGroupListElementRepresentation {

	private static final long serialVersionUID = 1L;

	public WeblinkGroupListElementRepresentationView(
			@Mapping("id") String id,
			@Mapping("owner.key") String ownerKey,
			@Mapping("creationDate") Calendar creationDate) {
		super(id, ownerKey, creationDate);
	}

	@JsonIgnore
	@IdMapping("id")
	public abstract String getId();
	
}
