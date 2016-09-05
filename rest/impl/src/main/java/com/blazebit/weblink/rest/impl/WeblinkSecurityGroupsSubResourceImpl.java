package com.blazebit.weblink.rest.impl;

import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.weblink.core.api.WeblinkSecurityGroupDataAccess;
import com.blazebit.weblink.core.model.jpa.Account;
import com.blazebit.weblink.rest.api.WeblinkSecurityGroupSubResource;
import com.blazebit.weblink.rest.api.WeblinkSecurityGroupsSubResource;
import com.blazebit.weblink.rest.impl.view.WeblinkSecurityGroupListElementRepresentationView;
import com.blazebit.weblink.rest.model.OwnerRepresentation;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupListElementRepresentation;
import com.blazebit.weblink.rest.model.WeblinkSecurityGroupListRepresentation;

import javax.inject.Inject;
import java.util.List;

public class WeblinkSecurityGroupsSubResourceImpl extends AbstractResource implements WeblinkSecurityGroupsSubResource {

    private Account owner;

    @Inject
    private WeblinkSecurityGroupDataAccess securityGroupDataAccess;

    public WeblinkSecurityGroupsSubResourceImpl(Account owner) {
        this.owner = owner;
    }

    @Override
    public WeblinkSecurityGroupListRepresentation get() {
        List<WeblinkSecurityGroupListElementRepresentation> list = (List<WeblinkSecurityGroupListElementRepresentation>) (List<?>) securityGroupDataAccess.findAllByAccountId(owner.getId(), EntityViewSetting.create(WeblinkSecurityGroupListElementRepresentationView.class));
        OwnerRepresentation ownerRepresentation = new OwnerRepresentation(owner.getKey(), owner.getName());
        WeblinkSecurityGroupListRepresentation result = new WeblinkSecurityGroupListRepresentation(ownerRepresentation, list);
        return result;
    }

    @Override
    public WeblinkSecurityGroupSubResource get(String securityGroupName) {
        return inject(new WeblinkSecurityGroupSubResourceImpl(owner, securityGroupName));
    }
}
