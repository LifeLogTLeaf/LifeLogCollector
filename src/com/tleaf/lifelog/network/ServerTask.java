package com.tleaf.lifelog.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;

import com.google.android.gms.internal.db;
import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.util.Mylog;

public class ServerTask implements DbAccessInterface {
	private static final String TAG = "서버통신";
	private static final String URL = "http://192.168.0.7:8080/api/lifelogs";
	private static final int SOCKET_TIMEOUT = 5000;
	private static final int CONNECTION_TIMEOUT = 5000;
	private static String httpMethod, reqeustName;
	private int resultcode; // 응답에따른 결과코드
	private OnDataListener listener;

	public ServerTask(String method, String requestName, OnDataListener onDataListener) {
		this.httpMethod = method;
		//this.reqeustName = reqeustName; //Test용 나중에 변경할꺼임.
		this.listener = onDataListener;
	}


	/* 데이터베이스  CRUD */
	@Override
	public void getData(DbAccessOption option) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "서버에서 데이터를 가져옵니다.");
		DbTask dbTask = new DbTask();
		dbTask.execute();
	
	}

	@Override
	public void postData(DbAccessOption option, Lifelog document) {
		// TODO Auto-generated method stub
		DbTask dbTask = new DbTask();
		dbTask.execute();
	}
	
	
	private class DbTask extends AsyncTask<List<NameValuePair>, Void, String>{

		@Override
		protected String doInBackground(List<NameValuePair>... data) {
			// TODO Auto-generated method stub
			HttpClient client = new DefaultHttpClient();
			HttpParams HttpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(HttpParams,
					CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(HttpParams, SOCKET_TIMEOUT);
			StringBuilder builder = null;
			String result = null;

			if (httpMethod.equals("get")) {
				builder = requestGet(client);
			} else if (httpMethod.equals("post")) {
				// builder = requestPost(client, data);
			}

			if (builder != null) {
				result = builder.toString();
			}

			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (resultcode == 200) {
				if (httpMethod.equals("get")) {
					Mylog.i(TAG, "HTTP GET 완료" + result);
					listener.onSendData(result);
				} else if (httpMethod.equals("get")) {
				}
			}
		}
		
		public StringBuilder requestGet(HttpClient client) {
			StringBuilder builder = new StringBuilder();
			HttpGet httpGet = new HttpGet(URL);
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();

				resultcode = statusLine.getStatusCode();
				Mylog.i(TAG, "(Get) StatusCode = " + resultcode);
				if (resultcode == 200) {
					HttpEntity resultEntity = response.getEntity();
					InputStream result = resultEntity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(result));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return builder;
		}
	}
	

}