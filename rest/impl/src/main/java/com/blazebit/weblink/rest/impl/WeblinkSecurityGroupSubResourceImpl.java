package com.blazebit.weblink.rest.impl;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.*;
import com.blazebit.weblink.core.model.jpa.Account;
import com.blazebit.weblink.core.model.jpa.WeblinkSecurityGroup;
import com.blazebit.weblink.core.model.security.Role;
import com.blazebit.weblink.rest.api.WeblinkSecurityGroupSubResource;
import com.blazebit.weblink.rest.impl.view.WeblinkSecurityGroupRepresentationView;
import com.blazebit.weblink.rest.model.BlazeWeblinkHeaders;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupRepresentation;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupUpdateRepresentation;
import com.blazebit.weblink.rest.model.config.ConfigurationTypeConfigEntryRepresentation;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

public class WeblinkSecurityGroupSubResourceImpl extends AbstractResource implements WeblinkSecurityGroupSubResource {

    private Account owner;
    private String securityGroupName;

    @Inject
    private AccountDataAccess accountDataAccess;
    @Inject
    private WeblinkSecurityGroupDataAccess securityGroupDataAccess;
    @Inject
    private WeblinkSecurityGroupService securityGroupService;
    @Inject
    private WeblinkSecurityConstraintFactoryDataAccess securityConstraintFactoryDataAccess;

    public WeblinkSecurityGroupSubResourceImpl(Account owner, String securityGroupName) {
        this.owner = owner;
        this.securityGroupName = securityGroupName;
    }

    @Override
    public WeblinkSecurityGroupRepresentation get() {
        EntityViewSetting<WeblinkSecurityGroupRepresentationView, CriteriaBuilder<WeblinkSecurityGroupRepresentationView>> setting;
        setting = EntityViewSetting.create(WeblinkSecurityGroupRepresentationView.class);
        setting.addOptionalParameter("securityConstraintFactoryDataAccess", securityConstraintFactoryDataAccess);
        WeblinkSecurityGroupRepresentation result = securityGroupDataAccess.findByOwnerAndName(owner, securityGroupName, setting);
        if (result == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity("Weblink security group not found").build());
        }

        return result;
    }

    @Override
    public Response delete() {
        try {
            securityGroupService.delete(owner, securityGroupName);
            return Response.noContent().build();
        } catch (WeblinkSecurityGroupNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity("Weblink security group not found").build();
        }
    }

    @Override
    public Response put(WeblinkSecurityGroupUpdateRepresentation<ConfigurationTypeConfigEntryRepresentation> securityGroupUpdate, String ownerKey) {
        WeblinkSecurityGroup securityGroup = new WeblinkSecurityGroup(securityGroupName);

        if (ownerKey == null || ownerKey.isEmpty() || ownerKey.equals(userContext.getAccountKey())) {
            securityGroup.setOwner(owner);
        } else if (userContext.getAccountRoles().contains(Role.ADMIN)) {
            Account owner = accountDataAccess.findByKey(ownerKey);

            if (owner == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).header(BlazeWeblinkHeaders.ERROR_CODE, "AccountNotFound").type(MediaType.TEXT_PLAIN_TYPE).entity("Account does not exist").build());
            }

            securityGroup.setOwner(owner);
        } else {
            throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN).type(MediaType.TEXT_PLAIN_TYPE).entity("Only admins may change the owner").build());
        }

        securityGroup.setName(securityGroupName);
        securityGroup.setOwner(owner);
        securityGroup.setTags(securityGroup.getTags());
        securityGroup.setConstraintConfigurations(toMapList(securityGroupUpdate.getConfiguration()));

        securityGroupService.put(securityGroup);
        return Response.ok().build();
    }

    private List<Map<String, String>> toMapList(List<Set<ConfigurationTypeConfigEntryRepresentation>> configuration) {
        if (configuration == null) {
            return Collections.emptyList();
        }

        List<Map<String, String>> constraintConfigurations = new ArrayList<>(configuration.size());

        for (Set<ConfigurationTypeConfigEntryRepresentation> constraintConfig : configuration) {
            Map<String, String> configurationMap = new LinkedHashMap<>(constraintConfig.size());
            for (ConfigurationTypeConfigEntryRepresentation entry : constraintConfig) {
                configurationMap.put(entry.getKey(), entry.getValue());
            }

            constraintConfigurations.add(configurationMap);
        }

        return constraintConfigurations;
    }
}
