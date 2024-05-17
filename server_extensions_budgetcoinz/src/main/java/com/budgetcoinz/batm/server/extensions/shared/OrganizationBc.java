package com.budgetcoinz.batm.server.extensions.shared;

import com.generalbytes.batm.server.extensions.IOrganization;

public class OrganizationBc implements IOrganization {

    private String id;
    private String name;

    public OrganizationBc(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
