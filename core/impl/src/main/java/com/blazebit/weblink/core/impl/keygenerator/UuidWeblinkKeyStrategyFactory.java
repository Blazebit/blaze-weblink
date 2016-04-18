package com.blazebit.weblink.core.impl.keygenerator;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategy;
import com.blazebit.weblink.core.api.spi.WeblinkKeyStrategyFactory;
import com.blazebit.weblink.modules.dispatcher.base.DefaultProviderMetamodel;

@ApplicationScoped
public class UuidWeblinkKeyStrategyFactory implements WeblinkKeyStrategyFactory {
	
	private static final String KEY = "uuid";
	
	private final ProviderMetamodel metamodel;
	
	public UuidWeblinkKeyStrategyFactory() {
		this.metamodel = new DefaultProviderMetamodel(KEY, "UUID key generation strategy", "Uses the string representation of an uuid" 
		);
	}

	@Override
	public ProviderMetamodel getMetamodel() {
		return metamodel;
	}

	@Override
	public WeblinkKeyStrategy createWeblinkKeyStrategy(Map<String, ? extends Object> properties) {
		return BeanProvider.injectFields(new UuidWeblinkKeyStrategy());
	}

}
