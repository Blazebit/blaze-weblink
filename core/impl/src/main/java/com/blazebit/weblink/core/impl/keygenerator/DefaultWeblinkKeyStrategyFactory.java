package com.blazebit.weblink.core.impl.keygenerator;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategy;
import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategyFactory;
import com.blazebit.weblink.modules.dispatcher.base.DefaultProviderMetamodel;

@ApplicationScoped
public class DefaultWeblinkKeyStrategyFactory implements WeblinkKeyStrategyFactory {
	
	private static final String KEY = "default";
	
	private final ProviderMetamodel metamodel;
	
	public DefaultWeblinkKeyStrategyFactory() {
		this.metamodel = new DefaultProviderMetamodel(KEY, "Default key generation strategy", "Derives a small string key from an integer" 
		);
	}

	@Override
	public ProviderMetamodel getMetamodel() {
		return metamodel;
	}

	@Override
	public WeblinkKeyStrategy createWeblinkKeyStrategy(Map<String, ? extends Object> properties) {
		return BeanProvider.injectFields(new DefaultWeblinkKeyStrategy());
	}

}
