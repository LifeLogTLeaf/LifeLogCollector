package com.tleaf.lifelog.model;

import java.util.Map;

public class Location extends Document {

    private double latitude;
	private double longitude;
	private long currenttimemilisec;

	
    public Location() {
    	this.latitude = 0;
    	this.longitude = 0;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    

	public long getCurrenttimemilisec() {
		return currenttimemilisec;
	}

	public void setCurrenttimemilisec(long currenttimemilisec) {
		this.currenttimemilisec = currenttimemilisec;
	}

    @Override
	public void setMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("longitude", this.longitude);
		map.put("latitude", this.latitude);
		map.put("currenttimemilisec", this.currenttimemilisec);
		map.put("type", super.getType());
		map.put("uploadTime", super.getUploadtime());
    }

}
