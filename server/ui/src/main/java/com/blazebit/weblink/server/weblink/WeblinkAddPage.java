package com.blazebit.weblink.server.weblink;

import com.blazebit.weblink.rest.model.WeblinkSecurityGroupListElementRepresentation;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
public class WeblinkAddPage extends WeblinkBasePage {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WeblinkAddPage.class.getName());

	private List<SelectItem> securityGroupItems;
	
	public String add() {
		try {
			if (group != null && !group.isEmpty() && key != null && !key.isEmpty()) {
				put();
				return "/weblink/detail.xhtml?group=" + group + "&key=" + key + "&faces-redirect=true";
			}

			String message = "Invalid empty key!";
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			return null;
		} catch (RuntimeException ex) {
			String message = "Could not add weblink";
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			LOG.log(Level.SEVERE, "Could not add weblink " + key, ex);
			return null;
		}
	}

	public void onOwnerAccountChanged() {
		if (account == null) {
			securityGroupItems = new ArrayList<>(0);
			return;
		}
		
		List<WeblinkSecurityGroupListElementRepresentation> securityGroups = client.accounts().get(account).getSecurityGroups().get().getSecurityGroups();
		securityGroupItems = new ArrayList<>(securityGroups.size());
		
		for (WeblinkSecurityGroupListElementRepresentation securityGroup : securityGroups) {
			securityGroupItems.add(new SelectItem(securityGroup.getName(), securityGroup.getName()));
		}
	}

	public List<SelectItem> getSecurityGroupItems() {
		return securityGroupItems;
	}
}
