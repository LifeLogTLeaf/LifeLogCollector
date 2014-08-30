package com.tleaf.lifelog.model;

import java.util.Map;

public class UserInfo {
	private String userName;
	private String gender;
	private String userId;
	private FacebookUserInfo userFacebookInfo;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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
