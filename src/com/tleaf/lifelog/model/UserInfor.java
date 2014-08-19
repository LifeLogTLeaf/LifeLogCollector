package com.tleaf.lifelog.model;

import java.util.Map;

public class UserInfor extends Document {
	private String userName;
	private String gender;
	private FacebookUserInfor userFacebookInfor;

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

	public FacebookUserInfor getUserFacebookUserInfor() {
		return userFacebookInfor;
	}

	public void setUserFacebookUserInfor(FacebookUserInfor userFacebookUserInfor) {
		this.userFacebookInfor = userFacebookUserInfor;
	}

	@Override
	public void setMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
	}

}
