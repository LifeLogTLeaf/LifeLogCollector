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
		map.put("filename", this.fileName);
		map.put("imgpath", this.imgPath);
		map.put("longitude", super.getLongitude());
		map.put("latitude", super.getLatitude());
		map.put("locationtime", super.getLocationTime());
		map.put("type", super.getType());
		map.put("logtime", super.getLogTime());
	}
}
