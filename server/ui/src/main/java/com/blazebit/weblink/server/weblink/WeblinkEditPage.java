package com.blazebit.weblink.server.weblink;


import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class WeblinkEditPage extends WeblinkAddPage {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WeblinkEditPage.class.getName());

	@Override
	protected void init() {
		if (weblink != null) {
			onOwnerAccountChanged();
		}
	}
	
	public String update() {
		try {
			put();
			return null;
		} catch (RuntimeException ex) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not update weblink", null));
			LOG.log(Level.SEVERE, "Could not update weblink " + key, ex);
			return null;
		}
	}
	
}
