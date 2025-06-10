package com.budgetcoinz.batm.server.extensions.rest.identity.models;

import java.util.Date;

public class IdentityPersonalPieceInfoUpdateModel {
    private String firstname;
    private String lastname;
    private String contactAddress;
    private String contactCity;
    private String contactProvince;
    private String contactZIP;
    private Date dateOfBirth;
    private Integer documentType;
    private String idCardNumber;
    private Date documentValidTo;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactCity() {
        return contactCity;
    }

    public void setContactCity(String contactCity) {
        this.contactCity = contactCity;
    }

    public String getContactProvince() {
        return contactProvince;
    }

    public void setContactProvince(String contactProvince) {
        this.contactProvince = contactProvince;
    }

    public String getContactZIP() {
        return contactZIP;
    }

    public void setContactZIP(String contactZIP) {
        this.contactZIP = contactZIP;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public Date getDocumentValidTo() {
        return documentValidTo;
    }

    public void setDocumentValidTo(Date documentValidTo) {
        this.documentValidTo = documentValidTo;
    }
}
