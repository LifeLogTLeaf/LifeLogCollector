package com.tleaf.lifelog.network;

import android.content.Context;

public class DbAccessOption {
	private String dbType;
	private String dbName;
	private String requestMethod;
	private String url;
	private Context context;
	private OnDataListener listener;

	public OnDataListener getListener() {
		return listener;
	}

	public void setListener(OnDataListener listener) {
		this.listener = listener;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
