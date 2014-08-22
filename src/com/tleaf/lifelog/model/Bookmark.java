package com.tleaf.lifelog.model;

import java.util.Map;

public class Bookmark extends Lifelog {
	private String title;
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSiteUrl() {
		return url;
	}

	public void setSiteUrl(String rrl) {
		this.url = url;
	}

	@Override
	public void setMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("title", this.title);
		map.put("url", this.url);
		map.put("longitude", super.getLongitude());
		map.put("latitude", super.getLatitude());
		map.put("locationtime", super.getLocationTime());
		map.put("type", super.getType());
		map.put("logtime", super.getLogTime());
	}
}
