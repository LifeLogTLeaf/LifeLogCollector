package com.tleaf.lifelog.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Preference {
	private SharedPreferences pref;
	private SharedPreferences.Editor edit;

	public Preference(Context context) {
		pref = context.getSharedPreferences("lifelog_pref", 0);
		edit = pref.edit();
	}

	public void setStringPref(String key, String value) {
		edit.putString(key, value); // 저장
		edit.commit();
	}

	public String getStringPref(String key) {
		return pref.getString(key, "no data"); //defalutVaule
	}

	public void setBooleanPref(String key, Boolean value) {
		edit.putBoolean(key, value); // 저장
		edit.commit();
		Log.i("prefrence", "prefrence boolean : " + key + "," + value);
	}

	public boolean getBooleanPref(String key) {
		return pref.getBoolean(key, false);
	}

	
	public void setLongPref(String key, long value) {
		edit.putLong(key, value);
		edit.commit();
	}

	public long getLongPref(String key) {
		return pref.getLong(key, 0); //defalutVaule
	}

}
