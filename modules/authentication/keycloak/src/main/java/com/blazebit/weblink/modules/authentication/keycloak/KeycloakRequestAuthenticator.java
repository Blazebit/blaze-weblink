package com.blazebit.weblink.modules.authentication.keycloak;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;

import com.blazebit.weblink.modules.authentication.api.RequestAuthenticator;

public class KeycloakRequestAuthenticator implements RequestAuthenticator {

	@Override
	public String getAccountKey(HttpServletRequest request) {
		IDToken token = AdminClient.getIDToken(request);
		
		if (token == null) {
			return null;
		}
		
		return token.getId();
	}

	@Override
	public Set<String> getAccountRoles(HttpServletRequest request, Set<String> allRoles) {
		KeycloakSecurityContext context = getSecurityContext(request);
		return context.getToken().getRealmAccess().getRoles();
	}
	
	public KeycloakSecurityContext getSecurityContext(HttpServletRequest request) {
		Principal userPrincipal = request.getUserPrincipal();
		if(userPrincipal == null || !(userPrincipal instanceof KeycloakPrincipal)){
			return emptySecurityContext();
		}

		KeycloakSecurityContext context = ((KeycloakPrincipal<?>) userPrincipal).getKeycloakSecurityContext();
		if (context == null) {
			return emptySecurityContext();
		}
		
		return context;
	}
	
	private KeycloakSecurityContext emptySecurityContext() {
		return new KeycloakSecurityContext();
	}

}
