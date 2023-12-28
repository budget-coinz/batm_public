package com.budgetcoinz.batm.server.extensions.rest.edd.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EddMetaObject {
    @JsonProperty("latitude")
    public String latitude;

    @JsonProperty("longitude")
    public String longitude;

    @JsonProperty("city")
    public String city;

    @JsonProperty("region")
    public String region;

    @JsonProperty("country")
    public String country;

    @JsonProperty("postal")
    public String postal;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getPostal() {
        return postal;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }
}
