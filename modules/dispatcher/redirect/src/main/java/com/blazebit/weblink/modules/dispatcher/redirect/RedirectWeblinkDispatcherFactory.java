package com.blazebit.weblink.modules.dispatcher.redirect;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcher;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcherFactory;
import com.blazebit.weblink.modules.dispatcher.base.DefaultProviderMetamodel;

@ApplicationScoped
public class RedirectWeblinkDispatcherFactory implements WeblinkDispatcherFactory {
	
	private static final String KEY = "redirect";
	
	private final ProviderMetamodel metamodel;
	
	public RedirectWeblinkDispatcherFactory() {
		this.metamodel = new DefaultProviderMetamodel(KEY, "Redirect dispatcher", "Redirects the request" 
		);
	}

	@Override
	public ProviderMetamodel getMetamodel() {
		return metamodel;
	}

	@Override
	public WeblinkDispatcher createWeblinkDispatcher(Map<String, ? extends Object> properties) {
		return new RedirectWeblinkDispatcher();
	}

}
