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
}
