package com.blazebit.weblink.core.impl.security;

import com.blazebit.weblink.core.api.spi.WeblinkSecurityConstraint;
import com.blazebit.weblink.core.model.jpa.WeblinkId;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IpRangeSecurityConstraint implements WeblinkSecurityConstraint {

    private static final Logger LOG = Logger.getLogger(IpRangeSecurityConstraint.class.getName());

    private final long networkBaseAddr;
    private final long netmaskAddr;

    public IpRangeSecurityConstraint(long networkBaseAddr, long netmaskAddr) {
        this.networkBaseAddr = networkBaseAddr;
        this.netmaskAddr = netmaskAddr;
    }

    @Inject
    private HttpServletRequest request;

    @Override
    public Response validate(WeblinkId weblinkId) {
        long clientIp;
        try {
            final InetAddress clientAddress = InetAddress.getByName(request.getRemoteAddr());
            clientIp = ipToLong(clientAddress);
        } catch (UnknownHostException e) {
            LOG.log(Level.SEVERE, "Could not parse remote address!", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        if (networkBaseAddr == (clientIp & netmaskAddr)) {
            return null;
        }

        return Response.status(Response.Status.FORBIDDEN).build();
    }

    public static IpRangeSecurityConstraint create(String networkAddress, String netmaskAddress) {
        if (networkAddress == null || networkAddress.isEmpty()) {
            throw new IllegalArgumentException("Invalid empty network address!");
        }
        if (netmaskAddress == null || netmaskAddress.isEmpty()) {
            throw new IllegalArgumentException("Invalid empty netmask address!");
        }

        long networkAddrLong;
        long netmaskAddrLong;
        try {
            final InetAddress clientAddress = InetAddress.getByName(networkAddress);
            networkAddrLong = ipToLong(clientAddress);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Invalid network address!", e);
        }
        try {
            final InetAddress clientAddress = InetAddress.getByName(netmaskAddress);
            netmaskAddrLong = ipToLong(clientAddress);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Invalid netmask address!", e);
        }

        return new IpRangeSecurityConstraint(networkAddrLong & netmaskAddrLong, netmaskAddrLong);
    }

    private static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }
}
