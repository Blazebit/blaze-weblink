package com.blazebit.weblink.rest.impl.view;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.MappingSingular;
import com.blazebit.weblink.core.model.jpa.Weblink;
import com.blazebit.weblink.core.model.jpa.WeblinkId;

@EntityView(Weblink.class)
public interface WeblinkDispatchView extends Serializable {

	@IdMapping("id")
	public WeblinkId getId();
	
	public URI getTargetUri();
	
	public String getDispatcherType();
	
	@MappingSingular
	public Map<String, String> getDispatcherConfiguration();
}
