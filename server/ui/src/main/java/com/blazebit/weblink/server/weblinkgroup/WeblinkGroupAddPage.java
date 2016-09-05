package com.blazebit.weblink.server.weblinkgroup;

import com.blazebit.weblink.rest.model.WeblinkSecurityGroupListElementRepresentation;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupListRepresentation;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class WeblinkGroupAddPage extends WeblinkGroupBasePage {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WeblinkGroupAddPage.class.getName());

	public String add() {
		try {
			if (name != null || name.isEmpty()) {
				put();
				return "/weblink-group/detail.xhtml?account=" + account + "&name=" + name + "&faces-redirect=true";
			}

			String message = "Invalid empty name!";
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			return null;
		} catch (RuntimeException ex) {
			String message = "Could not add weblink group";
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			LOG.log(Level.SEVERE, "Could not add weblink group" + name, ex);
			return null;
		}
	}
	
}
