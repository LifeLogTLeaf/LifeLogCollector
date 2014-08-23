package com.tleaf.lifelog.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.model.Call;
import com.tleaf.lifelog.model.FacebookUserInfo;
import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.model.Location;
import com.tleaf.lifelog.model.Photo;
import com.tleaf.lifelog.model.Sms;
import com.tleaf.lifelog.model.UserInfo;

public class ResponseConverter {

	public Lifelog getLifelog(String document) throws JSONException {
		JSONObject jsonobj;

		try {
			jsonobj = new JSONObject(document);
		} catch (JSONException e) {
			e.printStackTrace();
			jsonobj = new JSONObject();
		}

		switch (jsonobj.getString("type")) {
		case "bookmark":
			return getBookmark(jsonobj);
		case "location":
			return getLocation(jsonobj);
		case "call":
			return getCall(jsonobj);
		case "photo":
			return getPhoto(jsonobj);
		case "sms":
			return getSms(jsonobj);
		case "userinfo":
			return getUserInfo(jsonobj);
		default:
			throw new JSONException(
					"Lifelog Type is Invalid or String isn't a Lifelog");
		}

	}

	public Bookmark getBookmark(JSONObject json) throws JSONException {
		Bookmark bookmark = new Bookmark();
		bookmark.setId(json.getString("_id"));
		bookmark.setRev(json.getString("_rev"));
		bookmark.setLogTime(json.getLong("logtime"));
		bookmark.setType(json.getString("type"));
		bookmark.setLatitude(json.getDouble("latitude"));
		bookmark.setLongitude(json.getDouble("longitude"));
		bookmark.setLocationTime(json.getLong("locationtime"));

		bookmark.setSiteUrl(json.getString("url"));
		bookmark.setTitle(json.getString("title"));

		return bookmark;
	}

	public Call getCall(JSONObject json) throws JSONException {
		Call call = new Call();
		call.setId(json.getString("_id"));
		call.setRev(json.getString("_rev"));
		call.setLogTime(json.getLong("logtime"));
		call.setType(json.getString("type"));
		call.setLatitude(json.getDouble("latitude"));
		call.setLongitude(json.getDouble("longitude"));
		call.setLocationTime(json.getLong("locationtime"));

		call.setDate(json.getString("date"));
		call.setName(json.getString("name"));
		call.setNumber(json.getString("number"));

		return call;
	}

	public Location getLocation(JSONObject json) throws JSONException {
		Location location = new Location();
		location.setId(json.getString("_id"));
		location.setRev(json.getString("_rev"));
		location.setLogTime(json.getLong("logtime"));
		location.setType(json.getString("type"));
		location.setLatitude(json.getDouble("latitude"));
		location.setLongitude(json.getDouble("longitude"));
		location.setLocationTime(json.getLong("locationtime"));

		return location;
	}

	public Photo getPhoto(JSONObject json) throws JSONException {
		Photo photo = new Photo();
		photo.setId(json.getString("_id"));
		photo.setRev(json.getString("_rev"));
		photo.setLogTime(json.getLong("logtime"));
		photo.setType(json.getString("type"));
		photo.setLatitude(json.getDouble("latitude"));
		photo.setLongitude(json.getDouble("longitude"));
		photo.setLocationTime(json.getLong("locationtime"));

		return photo;
	}

	public Sms getSms(JSONObject json) throws JSONException {
		Sms sms = new Sms();
		sms.setId(json.getString("_id"));
		sms.setRev(json.getString("_rev"));
		sms.setLogTime(json.getLong("logtime"));
		sms.setType(json.getString("type"));
		sms.setLatitude(json.getDouble("latitude"));
		sms.setLongitude(json.getDouble("longitude"));
		sms.setLocationTime(json.getLong("locationtime"));

		sms.setAddress(json.getString("address"));
		sms.setBody(json.getString("body"));
		sms.setDate(json.getLong("date"));

		return sms;
	}

	public UserInfo getUserInfo(JSONObject json) throws JSONException {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(json.getString("_id"));
		userInfo.setRev(json.getString("_rev"));
		userInfo.setLogTime(json.getLong("logtime"));
		userInfo.setType(json.getString("type"));
		userInfo.setLatitude(json.getDouble("latitude"));
		userInfo.setLongitude(json.getDouble("longitude"));
		userInfo.setLocationTime(json.getLong("locationtime"));

		userInfo.setUserName(json.getString("username"));
		userInfo.setGender(json.getString("gender"));
		userInfo.setUserFacebookUserInfo(getFacebookUserInfo(json
				.getJSONObject("facebookuserinfor")));

		return userInfo;
	}

	public FacebookUserInfo getFacebookUserInfo(JSONObject json)
			throws JSONException {
		FacebookUserInfo facebookuserInfo = new FacebookUserInfo();
		facebookuserInfo.setFacebookAccesskey(json
				.getString("facebookaccesskey"));
		facebookuserInfo.setFacebookId(json.getString("facebookid"));
		facebookuserInfo.setFacebookLastpostDate(json
				.getString("facebooklastpostdate"));
		facebookuserInfo.setFacebookLastUpdateDate(json
				.getString("facebooklastupdatedate"));

		JSONArray jsonArray = json.getJSONArray("facebookpermission");

		ArrayList<String> facebookpermission = new ArrayList<String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			facebookpermission.add(jsonArray.getString(i));
		}
		facebookuserInfo.setFacebookPermission(facebookpermission);

		return facebookuserInfo;
	}

}