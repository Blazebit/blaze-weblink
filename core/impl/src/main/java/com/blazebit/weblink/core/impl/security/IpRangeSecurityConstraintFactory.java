package com.blazebit.weblink.core.impl.security;

import com.blazebit.weblink.core.api.WeblinkException;
import com.blazebit.weblink.core.api.spi.ProviderMetamodel;
import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraintFactory;
import com.blazebit.weblink.modules.dispatcher.base.DefaultConfigurationElement;
import com.blazebit.weblink.modules.dispatcher.base.DefaultProviderMetamodel;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import java.util.Map;

public class IpRangeSecurityConstraintFactory implements WeblinkSecurityConstraintFactory {

	private static final String KEY = "ip-range";

	private final ProviderMetamodel metamodel;

	public IpRangeSecurityConstraintFactory() {
		this.metamodel = new DefaultProviderMetamodel(KEY, "IP-Range constraint", "Restricts access to weblinks to clients from specific ip ranges",
				new DefaultConfigurationElement(IpRangeConstraint.NETWORK_ADDRESS_PROPERTY, "text", null, "IPv4 network address", "The network address"),
				new DefaultConfigurationElement(IpRangeConstraint.NETMASK_ADDRESS_PROPERTY, "text", null, "Netmask", "The netmask")
		);
	}

	@Override
	public ProviderMetamodel getMetamodel() {
		return metamodel;
	}

	@Override
	public IpRangeSecurityConstraint createConstraint(Map<String, ? extends Object> properties) {
		String networkAddress = getString(properties, IpRangeConstraint.NETWORK_ADDRESS_PROPERTY);
		String netmaskAddress = getString(properties, IpRangeConstraint.NETMASK_ADDRESS_PROPERTY);
		return BeanProvider.injectFields(IpRangeSecurityConstraint.create(networkAddress, netmaskAddress));
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
