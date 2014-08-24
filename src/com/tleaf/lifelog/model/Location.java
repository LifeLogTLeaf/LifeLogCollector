package com.tleaf.lifelog.model;

import java.util.Map;

public class Location extends Lifelog {
	@Override
	public void setMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("longitude", super.getLongitude());
		map.put("latitude", super.getLatitude());
		map.put("locationTime", super.getLocationTime());
		map.put("logType", super.getLogType());
		map.put("logTime", super.getLogTime());
	}

}
