package com.budgetcoinz.batm.server.extensions.rest.identity.models;

public class IdentityUpdateModel {
    private String publicId;
    private String ssn;
    private String automation;
    private String occupation;

    public String getPublicId() {
        return publicId;
    }
    public String getSsn() {
        return ssn;
    }
    public String getAutomation() {
        return automation;
    }
    public String getOccupation() { return occupation; }
}
