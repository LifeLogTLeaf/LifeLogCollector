package com.tleaf.lifelog.model;

import java.util.Map;

public class Photo extends Lifelog {
	private String fileName;
	private String imgPath;

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void setMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("fileName", this.fileName);
		map.put("imgPath", this.imgPath);
		map.put("longitude", super.getLongitude());
		map.put("latitude", super.getLatitude());
		map.put("locationTime", super.getLocationTime());
		map.put("logType", super.getLogType());
		map.put("logTime", super.getLogTime());
	}
}
