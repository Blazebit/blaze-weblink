package com.blazebit.weblink.server.faces.constraint;

import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigElementRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIData;
import javax.faces.component.UINamingContainer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@FacesComponent("constraintsComponent")
public class ConstraintsComponent extends UINamingContainer {

    private UIData list;
    
	public void addConstraint() {
		ConstraintEntry constraint = new ConstraintEntry();
		constraint.getConfigurationHolder().setConfiguration(new ArrayList<ConfigurationTypeConfigEntryRepresentation>());
		getValue().getConstraintEntries().add(new ConstraintEntry());
	}

    public void removeConstraint() {
        getValue().getConstraintEntries().remove(list.getRowData());
    }

    public void onTypeChanged(int index, List<ConfigurationTypeConfigElementRepresentation> configuration) {
		getValue().getConstraintEntries().get(index).getConfigurationHolder().setConfiguration(configuration);
	}
    
    protected ConstraintsHolder getValue() {
    	return (ConstraintsHolder) getAttributes().get("value");
    }

	public UIData getList() {
		return list;
	}

	public void setList(UIData list) {
		this.list = list;
	}
	
}
