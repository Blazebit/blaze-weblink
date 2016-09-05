package com.blazebit.weblink.server.weblink;

import com.blazebit.weblink.rest.model.WeblinkRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.logging.Logger;

@Named
@RequestScoped
public class WeblinkDetailPage extends WeblinkBasePage {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WeblinkDetailPage.class.getName());

	private String parent = "";
	
	@Override
	protected void init() {
		super.init();
		int slashIndex;
		if (weblink == null || key == null || key.isEmpty() || key.endsWith("/") || (slashIndex = key.lastIndexOf('/')) < 0) {
			this.parent = "";
		} else {
			this.parent = key.substring(0, slashIndex);
		}
	}
	
	public String getParent() {
		return parent;
	}
	
	public WeblinkRepresentation getWeblink() {
		return (WeblinkRepresentation) weblink;
	}
	
}
