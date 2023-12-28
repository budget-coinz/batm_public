package com.budgetcoinz.batm.server.extensions.rest.edd.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EddPropertyObject {
    @JsonProperty("name")
    public String name;

    @JsonProperty("value")
    public String value;

    @JsonProperty("id")
    public int id;

    @JsonProperty("type")
    public String type;

    @JsonProperty("first")
    private String first;

    @JsonProperty("middle")
    private String middle;

    @JsonProperty("last")
    private String last;

    @JsonProperty("visble")
    public boolean visible;

    @JsonProperty("value_raw")
    private String valueRaw;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getVisible() { return visible; }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getValueRaw() {
        return valueRaw;
    }

    public void setValueRaw(String valueRaw) {
        this.valueRaw = valueRaw;
    }
}
