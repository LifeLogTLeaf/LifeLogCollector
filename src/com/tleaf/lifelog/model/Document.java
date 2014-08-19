package com.tleaf.lifelog.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tleaf.lifelog.util.Util;

/**
 * Created by jangyoungjin on 8/10/14.
 */
public class Document {
    @JsonProperty("_id")
    protected String id;

    @JsonProperty("_rev")
    protected String rev;

    private long time;
    private Position position;

    private String type;

	public Document() {
    	this.time = Util.getCurrentTime();
    	this.position = Util.getCurrentPostion();
	}
    
    public Document(long time, Position position) {
    	this.time = time;
    	this.position = position;
    }
    
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
