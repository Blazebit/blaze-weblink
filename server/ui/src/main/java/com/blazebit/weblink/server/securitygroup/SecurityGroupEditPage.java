package com.blazebit.weblink.server.securitygroup;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class SecurityGroupEditPage extends SecurityGroupAddPage {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SecurityGroupEditPage.class.getName());
	
	public String update() throws IOException {
		try {
			put();
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "/security-group/edit.xhtml?account=" + accountKey + "&name=" + name + "&faces-redirect=true";
		} catch (RuntimeException ex) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not update security group", null));
			LOG.log(Level.SEVERE, "Could not update securitygroup " + name, ex);
			return null;
		}
	}
}
