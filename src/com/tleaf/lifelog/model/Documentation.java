package com.tleaf.lifelog.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by jangyoungjin on 8/10/14.
 */
public class Documentation {
    @JsonProperty("_id")
    protected String id;

    @JsonProperty("_rev")
    protected String rev;

    private String Data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
