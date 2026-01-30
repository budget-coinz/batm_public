package com.budgetcoinz.batm.server.extensions.rest.edd.models;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class EddObject {
    @JsonProperty("data")
    public Map<String, EddPropertyObject> data;

    @JsonProperty("ip")
    public String ip;

    @JsonProperty("meta")
    public EddMetaObject meta;

    public Map<String, EddPropertyObject> getData() {
        return data;
    }

    public void setData(Map<String, EddPropertyObject> data) {
        this.data = data;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public EddMetaObject getMeta() {
        return meta;
    }

    public void setMeta(EddMetaObject meta) {
        this.meta = meta;
    }
}
