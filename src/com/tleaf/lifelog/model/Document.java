package com.tleaf.lifelog.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by jangyoungjin on 8/10/14.
 */
public class Document {
    @JsonProperty("_id")
    protected String id;

    @JsonProperty("_rev")
    protected String rev;

    private long date;
    private String type;

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
    	this.date = date;
    }
}
