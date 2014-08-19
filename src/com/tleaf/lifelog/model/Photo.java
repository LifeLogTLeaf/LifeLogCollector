package com.tleaf.lifelog.model;



public class Photo extends Document {
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
