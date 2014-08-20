package com.tleaf.lifelog.model;

import java.util.Map;

public class Bookmark extends Document {
	private String title;
	private String siteUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	@Override
	public void setMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("title", this.title);
		map.put("siteUrl", this.siteUrl);
		map.put("type", super.getType());
		map.put("uploadTime", super.getUploadtime());
	}
}
