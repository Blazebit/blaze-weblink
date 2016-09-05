package com.blazebit.weblink.server.account;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AccountEditPage extends AccountAddPage {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(AccountEditPage.class.getName());
	
	public String update() {
		try {
			put();
			return null;
		} catch (RuntimeException ex) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not put account", null));
			LOG.log(Level.SEVERE, "Could not put account " + key, ex);
			return null;
		}
	}
}
