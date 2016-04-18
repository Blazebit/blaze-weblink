package com.blazebit.weblink.core.impl.matcher;

import java.util.Map;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkMatcher;
import com.blazebit.weblink.core.api.spi.WeblinkMatcherFactory;
import com.blazebit.weblink.modules.dispatcher.base.DefaultProviderMetamodel;

public class DefaultWeblinkMatcherFactory implements WeblinkMatcherFactory {

	private static final String KEY = "default";
	
	private final ProviderMetamodel metamodel;
	
	public DefaultWeblinkMatcherFactory() {
		this.metamodel = new DefaultProviderMetamodel(KEY, "Default weblink matcher", "For now, it just matches everything" 
		);
	}

	@Override
	public ProviderMetamodel getMetamodel() {
		return metamodel;
	}

	@Override
	public WeblinkMatcher createWeblinkMatcher(Map<String, ? extends Object> properties) {
		return BeanProvider.injectFields(new DefaultWeblinkMatcher());
	}
}
