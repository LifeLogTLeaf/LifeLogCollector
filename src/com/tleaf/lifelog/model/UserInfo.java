package com.tleaf.lifelog.model;

import java.util.Map;

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

	@Override
	public void setMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("username", this.userName);
		map.put("gender", this.gender);
		map.put("userfacebookinfo", this.userFacebookInfo);
		map.put("longitude", super.getLongitude());
		map.put("latitude", super.getLatitude());
		map.put("locationtime", super.getLocationTime());
		map.put("type", super.getType());
		map.put("logtime", super.getLogTime());
	}
}
