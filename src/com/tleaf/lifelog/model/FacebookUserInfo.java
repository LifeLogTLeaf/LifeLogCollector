package com.tleaf.lifelog.model;

import java.util.ArrayList;

public class FacebookUserInfo{
	private String facebookId;
	private String facebookAccesskey;
	private String facebookLastpostDate;
	private String facebookLastUpdateDate;
	private ArrayList<String> facebookPermission;
	
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public String getFacebookAccesskey() {
		return facebookAccesskey;
	}
	public void setFacebookAccesskey(String facebookAccesskey) {
		this.facebookAccesskey = facebookAccesskey;
	}
	public String getFacebookLastpostDate() {
		return facebookLastpostDate;
	}
	public void setFacebookLastpostDate(String facebookLastpostDate) {
		this.facebookLastpostDate = facebookLastpostDate;
	}
	public String getFacebookLastUpdateDate() {
		return facebookLastUpdateDate;
	}
	public void setFacebookLastUpdateDate(String facebookLastUpdateDate) {
		this.facebookLastUpdateDate = facebookLastUpdateDate;
	}
	public ArrayList<String> getFacebookPermission() {
		return facebookPermission;
	}
	public void setFacebookPermission(ArrayList<String> facebookPermission) {
		this.facebookPermission = facebookPermission;
	}
	
	
}
