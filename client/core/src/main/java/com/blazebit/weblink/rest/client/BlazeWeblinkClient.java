package com.blazebit.weblink.rest.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import com.blazebit.weblink.rest.api.*;
import org.glassfish.jersey.client.proxy.WebResourceFactory;

public class BlazeWeblinkClient implements BlazeWeblink, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String serverUrl;
	private final ClientRequestFilter[] requestFilters;
    private final transient Client client;
    
	private BlazeWeblinkClient(String serverUrl, ClientRequestFilter[] requestFilters) {
		this.serverUrl = serverUrl;
		this.requestFilters = requestFilters;
		
		this.client = ClientBuilder.newClient();
	}
	
	private WebTarget initTarget() {
		WebTarget clientTarget = client.target(serverUrl);
        
        for (ClientRequestFilter filter : requestFilters) {
        	clientTarget = clientTarget.register(filter);
        }
        
        return clientTarget;
	}

	public static BlazeWeblink getInstance(String serverUrl) {
		return new BlazeWeblinkClient(serverUrl, new ClientRequestFilter[0]);
	}

	public static BlazeWeblink getInstance(String serverUrl, ClientRequestFilter... requestFilters) {
		return new BlazeWeblinkClient(serverUrl, requestFilters);
	}

	@Override
	public String getExternalLink(String weblinkGroupName, String weblinkKey) {
		return UriBuilder.fromUri(serverUrl)
				.path(WeblinkGroupsResource.class)
				.path(WeblinkGroupsResource.class, "get")
				.path(WeblinkGroupsSubResource.class, "getGroup")
				.path(WeblinkGroupSubResource.class, "getWeblink")
				.path(WeblinkSubResource.class, "dispatch")
				.build(weblinkGroupName, weblinkKey)
				.toString();
	}

	@Override
	public AdminResource admin() {
		return WebResourceFactory.newResource(AdminResource.class, initTarget());
	}

	@Override
	public AccountsResource accounts() {
		return WebResourceFactory.newResource(AccountsResource.class, initTarget());
	}
	
	@Override
	public WeblinkGroupsSubResource weblinkGroups() {
		return WebResourceFactory.newResource(WeblinkGroupsResource.class, initTarget()).get();
	}
	
	@Override
	public MatcherTypesResource matcherTypes() {
		return WebResourceFactory.newResource(MatcherTypesResource.class, initTarget());
	}
	
	@Override
	public DispatcherTypesResource dispatcherTypes() {
		return WebResourceFactory.newResource(DispatcherTypesResource.class, initTarget());
	}
	
	@Override
	public KeyStrategiesResource keyStrategies() {
		return WebResourceFactory.newResource(KeyStrategiesResource.class, initTarget());
	}

	@Override
	public SecurityConstraintTypesResource securityConstraintTypes() {
		return WebResourceFactory.newResource(SecurityConstraintTypesResource.class, initTarget());
	}

	@Override
	public void close() {
        client.close();
    }
    
    // SERIALIZATION
    
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        set("client", ClientBuilder.newClient());
    }
    
    private void set(String fieldName, Object value) {
    	try {
	    	Field field = BlazeWeblinkClient.class.getDeclaredField(fieldName);
	    	field.setAccessible(true);
	    	field.set(this, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
