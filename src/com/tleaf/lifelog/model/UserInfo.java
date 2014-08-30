package com.tleaf.lifelog.model;

public class UserInfo extends Lifelog {
	private String userName;
	private String gender;
	private FacebookUserInfo userFacebookInfo;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public FacebookUserInfo getUserFacebookUserInfo() {
		return userFacebookInfo;
	}

	public void setUserFacebookUserInfo(FacebookUserInfo userFacebookUserInfo) {
		this.userFacebookInfo = userFacebookUserInfo;
	}
}
