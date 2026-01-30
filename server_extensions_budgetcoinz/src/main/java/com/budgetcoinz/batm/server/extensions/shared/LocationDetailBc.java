package com.budgetcoinz.batm.server.extensions.shared;

import com.generalbytes.batm.server.extensions.*;

import java.util.ArrayList;
import java.util.List;

public class LocationDetailBc implements ILocationDetail {
    private String name;
    private String contactAddress;
    private String city;
    private String country;
    private String countryIso2;
    private String province;
    private String zip;
    private String description;
    private String gpsLat;
    private String gpsLon;
    private String timeZone;
    private String publicId;
    private String externalLocationId;

    private PersonBc contactPerson;
    private OrganizationBc organization;
    private String cashCollectionCompany;
    private Integer terminalCapacity;
    private List<NoteBc> notes;
    private List<OpeningHoursBc> openingHours;
    private List<CashCollectionDayBc> cashCollectionDays = new ArrayList<>();

    public LocationDetailBc(String name, String contactAddress, String city, String country, String countryIso2, String province, String zip, String description, String gpsLat, String gpsLon, String timeZone, String externalLocationId, PersonBc contactPerson, OrganizationBc organization, String cashCollectionCompany, Integer terminalCapacity, List<NoteBc> notes, List<OpeningHoursBc> openingHours, List<CashCollectionDayBc> cashCollectionDays) {
        this.name = name;
        this.contactAddress = contactAddress;
        this.city = city;
        this.country = country;
        this.countryIso2 = countryIso2;
        this.province = province;
        this.zip = zip;
        this.description = description;
        this.gpsLat = gpsLat;
        this.gpsLon = gpsLon;
        this.timeZone = timeZone;
        this.publicId = publicId;
        this.externalLocationId = externalLocationId;
        this.contactPerson = contactPerson;
        this.organization = organization;
        this.cashCollectionCompany = cashCollectionCompany;
        this.terminalCapacity = terminalCapacity;
        this.notes = notes;
        this.openingHours = openingHours;
        this.cashCollectionDays = cashCollectionDays;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContactAddress() {
        return contactAddress;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getCountryIso2() {
        return countryIso2;
    }

    @Override
    public String getProvince() {
        return province;
    }

    @Override
    public String getZip() {
        return zip;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getGpsLat() {
        return gpsLat;
    }

    @Override
    public String getGpsLon() {
        return gpsLon;
    }

    @Override
    public String getTimeZone() {
        return timeZone;
    }

    @Override
    public String getPublicId() {
        return publicId;
    }

    @Override
    public String getExternalLocationId() {
        return externalLocationId;
    }

    @Override
    public IPerson getContactPerson() {
        return contactPerson;
    }

    @Override
    public IOrganization getOrganization() {
        return organization;
    }

    @Override
    public String getCashCollectionCompany() {
        return cashCollectionCompany;
    }

    @Override
    public Integer getTerminalCapacity() {
        return terminalCapacity;
    }

    @Override
    public List<INote> getNotes() {
        return new ArrayList<>(notes);
    }

    @Override
    public List<IOpeningHours> getOpeningHours() {
        return new ArrayList<>(openingHours);
    }

    @Override
    public List<ICashCollectionDay> getCashCollectionDays() {
        return new ArrayList<>(cashCollectionDays);
    }
}
