package com.blazebit.weblink.rest.impl.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.weblink.core.model.jpa.WeblinkSecurityGroup;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupListElementRepresentation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Calendar;

@EntityView(WeblinkSecurityGroup.class)
public abstract class WeblinkSecurityGroupListElementRepresentationView extends WeblinkSecurityGroupListElementRepresentation {

	private static final long serialVersionUID = 1L;

	public WeblinkSecurityGroupListElementRepresentationView(
			@Mapping("name") String name,
			@Mapping("creationDate") Calendar creationDate) {
		super(name, creationDate);
	}

	@JsonIgnore
	@IdMapping("id")
	public abstract Long getId();
	
}
