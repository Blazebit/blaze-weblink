package com.blazebit.weblink.server.weblinkgroup;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class WeblinkGroupEditPage extends WeblinkGroupAddPage {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WeblinkGroupEditPage.class.getName());
	
	public String update() {
		try {
			put();
			return null;
		} catch (RuntimeException ex) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not put weblink group", null));
			LOG.log(Level.SEVERE, "Could not put weblink group " + name, ex);
			return null;
		}
	}
	
}
