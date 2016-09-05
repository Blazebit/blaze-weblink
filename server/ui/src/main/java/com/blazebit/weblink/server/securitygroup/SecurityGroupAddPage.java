package com.blazebit.weblink.server.securitygroup;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class SecurityGroupAddPage extends SecurityGroupBasePage {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SecurityGroupAddPage.class.getName());
	
	public String add() {
		try {
			if (name != null || name.isEmpty()) {
				put();
				return "/security-group/detail.xhtml?account=" + accountKey + "&name=" + name + "&faces-redirect=true";
			}

			String message = "Invalid empty name!";
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			return null;
		} catch (RuntimeException ex) {
			String message = "Could not add security group";
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			LOG.log(Level.SEVERE, "Could not add security group " + name, ex);
			return null;
		}
	}

}
