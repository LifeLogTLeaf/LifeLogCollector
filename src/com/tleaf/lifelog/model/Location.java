package com.tleaf.lifelog.model;

import java.util.Map;


public class Location extends Document {	
	private double longitude;
	private double latitude;
	private long currenttimemilisec;
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
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
		map.put("date", super.getDate());
	}

}
