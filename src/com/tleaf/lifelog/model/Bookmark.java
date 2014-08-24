package com.tleaf.lifelog.model;

import java.util.Map;

public class Bookmark extends Lifelog {
	private String title;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("title", this.title);
		map.put("url", this.url);
		map.put("longitude", super.getLongitude());
		map.put("latitude", super.getLatitude());
		map.put("locationTime", super.getLocationTime());
		map.put("logType", super.getLogType());
		map.put("logTime", super.getLogTime());
	}
}
