package com.blazebit.weblink.server.weblinkgroup;

import com.blazebit.weblink.rest.model.WeblinkGroupRepresentation;
import com.blazebit.weblink.server.account.AccountSupport;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

@Named
@RequestScoped
public class WeblinkGroupDetailPage extends WeblinkGroupBasePage {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(WeblinkGroupDetailPage.class.getName());
	
	@Inject
	private AccountSupport accountSupport;

	private String accountName;
	
	@Override
	protected void init() {
		super.init();
		if (weblinkGroup == null) {
			this.accountName = null;
		} else {
			this.accountName = accountSupport.getAccountName(((WeblinkGroupRepresentation) weblinkGroup).getOwnerKey());
		}
	}
	
	public WeblinkGroupRepresentation getWeblinkGroup() {
		return (WeblinkGroupRepresentation) weblinkGroup;
	}

	public String getAccountName() {
		return accountName;
	}
	
}
