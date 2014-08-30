package com.tleaf.lifelog.dbaccess;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.tleaf.lifelog.model.Lifelog;

public class DBOption {
	private String method;
	private Context context;
	private String url;
	private Lifelog lifelog;
	private Object data;
	private List listData;
	private String dbName;
	private Map<String, String> requestParams;
	private DBListener listener;

	public List getListData() {
		return listData;
	}

	public void setListData(List listData) {
		this.listData = listData;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Lifelog getLifelog() {
		return lifelog;
	}

	public void setLifelog(Lifelog lifelog) {
		this.lifelog = lifelog;
	}

	public DBListener getListener() {
		return listener;
	}

	public void setListener(DBListener listener) {
		this.listener = listener;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, String> requestParams) {
		this.requestParams = requestParams;
	}

}
