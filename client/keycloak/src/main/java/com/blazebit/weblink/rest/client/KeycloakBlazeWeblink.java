package com.blazebit.weblink.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.keycloak.admin.client.Config;
import org.keycloak.representations.AccessTokenResponse;

import com.blazebit.weblink.rest.client.BlazeWeblink;
import com.blazebit.weblink.rest.client.BlazeWeblinkClient;

public class KeycloakBlazeWeblink {

	public static BlazeWeblink getInstance(String serverUrl, AccessTokenResponse accessToken, Config keycloakConfig) {
		Client client = ClientBuilder.newClient();
		TokenManager tokenManager = new TokenManager(accessToken, keycloakConfig, client);
		return BlazeWeblinkClient.getInstance(serverUrl, new BearerAuthFilter(tokenManager));
	}
	
	public static BlazeWeblink getInstance(String serverUrl, String tokenString) {
		return BlazeWeblinkClient.getInstance(serverUrl, new BearerAuthFilter(tokenString));
	}

	public static BlazeWeblink getInstance(String serverUrl, String keycloakServerUrl, String realm, String username, String password, String clientId, String clientSecret) {
		return getInstance(serverUrl, null, new Config(keycloakServerUrl, realm, username, password, clientId, clientSecret));
	}
	
	public static BlazeWeblink getInstance(String serverUrl, String keycloakServerUrl, String realm, String username, String password, String clientId) {
		return getInstance(serverUrl, null, new Config(keycloakServerUrl, realm, username, password, clientId, null));
	}
}
