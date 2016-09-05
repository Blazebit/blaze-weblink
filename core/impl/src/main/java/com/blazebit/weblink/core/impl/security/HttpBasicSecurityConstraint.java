package com.blazebit.weblink.core.impl.security;

import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraint;
import com.blazebit.weblink.core.model.jpa.WeblinkId;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpBasicSecurityConstraint implements WeblinkSecurityConstraint {

    private static final Logger LOG = Logger.getLogger(HttpBasicSecurityConstraint.class.getName());

    private final String user;
    private final String password;

    @Inject
    private HttpServletRequest request;

    public HttpBasicSecurityConstraint(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public Response validate(WeblinkId weblinkId) {
        String auth = request.getHeader("Authorization");

        if (!isAuthorized(auth)) {
            return Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", "BASIC realm=\"weblink\"").build();
        }

        return null;
    }

    private boolean isAuthorized(String auth) {
        if (auth == null) {
            return false;  // no auth
        }
        if (!auth.toUpperCase().startsWith("BASIC ")) {
            return false;  // we only do BASIC
        }
        String userpassEncoded = auth.substring(6);
        String decoded = new String(DatatypeConverter.parseBase64Binary(userpassEncoded));
        int idx = decoded.indexOf(':');
        String user = decoded.substring(0, idx);
        String password = decoded.substring(idx + 1);

        if (this.user.equals(user) && this.password.equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
