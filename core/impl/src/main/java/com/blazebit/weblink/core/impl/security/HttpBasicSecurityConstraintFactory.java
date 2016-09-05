package com.blazebit.weblink.core.impl.security;

import com.blazebit.weblink.core.api.WeblinkException;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraintFactory;
import com.blazebit.weblink.modules.dispatcher.base.DefaultConfigurationElement;
import com.blazebit.weblink.modules.dispatcher.base.DefaultProviderMetamodel;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import java.util.Map;

public class HttpBasicSecurityConstraintFactory implements WeblinkSecurityConstraintFactory {

	private static final String KEY = "http-basic";

	private final ProviderMetamodel metamodel;

	public HttpBasicSecurityConstraintFactory() {
		this.metamodel = new DefaultProviderMetamodel(KEY, "HTTP-Basic constraint", "Restricts access to weblinks to clients that authenticate via HTTP basic with the configured credentials",
				new DefaultConfigurationElement(HttpBasicConstraint.USER_PROPERTY, "text", null, "Username", "The required username"),
				new DefaultConfigurationElement(HttpBasicConstraint.PASSWORD_PROPERTY, "text", null, "Password", "The required password")
		);
	}

	@Override
	public ProviderMetamodel getMetamodel() {
		return metamodel;
	}

	@Override
	public HttpBasicSecurityConstraint createConstraint(Map<String, ? extends Object> properties) {
		String user = getString(properties, HttpBasicConstraint.USER_PROPERTY);
		String password = getString(properties, HttpBasicConstraint.PASSWORD_PROPERTY);
		return BeanProvider.injectFields(new HttpBasicSecurityConstraint(user, password));
	}

	private String getString(Map<String, ?> properties, String propertyName) {
		Object o = properties.get(propertyName);

		if (o == null) {
			throw new WeblinkException("The property '" + propertyName + "' is not set!");
		}

		if (!(o instanceof String)) {
			throw new WeblinkException("Invalid value for property '" + propertyName + "' is set! String expected but got: " + o);
		}

		return (String) o;
	}
}
