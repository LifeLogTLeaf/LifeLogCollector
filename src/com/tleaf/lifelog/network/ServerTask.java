package com.tleaf.lifelog.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;

import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.util.Mylog;

/**
 * 2014.08.24
 * @author jangyoungjin
 * requestName 반드시 문자열이여한다.
 */
public class ServerTask implements DbAccessInterface {
	private static final String TAG = "서버통신";
	private static final String URL = "http://54.191.147.237:8080/lifelog/api/"; //사설 아이피 
	private static final int SOCKET_TIMEOUT = 5000;
	private static final int CONNECTION_TIMEOUT = 5000;
	private String requestMethod, dbName, requestUrl;
	private int resultcode; // 응답에따른 결과코드
	private OnDataListener listener;

	public ServerTask(){
	}


	/* 데이터베이스  CRUD */
	@Override
	public void getData(DbAccessOption option) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "서버에서 데이터를 가져옵니다.");
		DbTask dbTask = new DbTask();
		dbName = option.getDbName();
		requestMethod = option.getRequestMethod();
		requestUrl = option.getUrl();
		listener = option.getListener();
		dbTask.execute();
	
	}
	

	@Override
	public void postData(DbAccessOption option, Lifelog document) {
		// TODO Auto-generated method stub
		DbTask dbTask = new DbTask();
		dbTask.execute();
	}
	
	//@issue
	//나중에 배열로 파라미터의 종류를 받는다.
	private String getURI(){
		String url;

		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", dbName));
		String paramString = URLEncodedUtils.format(params, "utf-8");
		
		url = URL + requestUrl + "?" + paramString;
		Mylog.i(TAG, url);
		return url;
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

			if (requestMethod.equals("get")) {
				builder = requestGet(client);
			} else if (requestMethod.equals("post")) {
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
				if (requestMethod.equals("get")) {
					Mylog.i(TAG, "HTTP GET 완료" + result);
					listener.onSendData(result);
				} else if (requestMethod.equals("get")) {
				}
			}
		}
		
		public StringBuilder requestGet(HttpClient client) {
			StringBuilder builder = new StringBuilder();
			HttpGet httpGet = new HttpGet(getURI());
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