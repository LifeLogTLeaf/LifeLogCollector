package com.tleaf.lifelog.util.network;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;

public class HttpTask extends AsyncTask<List<NameValuePair>, Void, String> {
    private static final int SOCKET_TIMEOUT     = 5000;
    private static final int CONNECTION_TIMEOUT = 5000;
    private static String httpMethod;


	@Override
	protected String doInBackground(List<NameValuePair>... data) {
		// TODO Auto-generated method stub
		HttpClient client = new DefaultHttpClient();
		HttpParams HttpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(HttpParams, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(HttpParams, SOCKET_TIMEOUT);
		StringBuilder builder = null;
		String result = null;

		if (httpMethod.equals("get")) {
			//builder = requestGet(client);
		} else if (httpMethod.equals("post")) {
			//builder = requestPost(client, data);
		}

		if (builder != null) {
			result = builder.toString();
		}

		return result;
	}

}
