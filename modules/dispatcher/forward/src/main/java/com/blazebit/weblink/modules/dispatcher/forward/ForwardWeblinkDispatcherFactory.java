package com.blazebit.weblink.modules.dispatcher.forward;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcher;
import com.blazebit.weblink.core.api.spi.WeblinkDispatcherFactory;
import com.blazebit.weblink.modules.dispatcher.base.DefaultProviderMetamodel;

@ApplicationScoped
public class ForwardWeblinkDispatcherFactory implements WeblinkDispatcherFactory {
	
	private static final String KEY = "forward";
	
	private final ProviderMetamodel metamodel;
	
	public ForwardWeblinkDispatcherFactory() {
		this.metamodel = new DefaultProviderMetamodel(KEY, "Forward dispatcher", "Forwards the request" 
		);
	}

	@Override
	public ProviderMetamodel getMetamodel() {
		return metamodel;
	}

	@Override
	public WeblinkDispatcher createWeblinkDispatcher(Map<String, ? extends Object> properties) {
		return new ForwardWeblinkDispatcher();
	}

}
