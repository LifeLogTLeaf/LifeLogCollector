package com.tleaf.lifelog.dbaccess.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.tleaf.lifelog.dbaccess.DBListener;
import com.tleaf.lifelog.dbaccess.DBOption;
import com.tleaf.lifelog.util.Mylog;

/**
 * 2014.08.27 서버에 직접 접근하는 클래스
 * 
 * @author jangyoungjin
 * 
 */
public class ServerDB implements DBInterface {
	private static final String TAG = "서버DB";
	private String URL = "http://192.168.0.10:8080/api/";
	// 소켓옵션
	private static final int SOCKET_TIMEOUT = 5000;
	private static final int CONNECTION_TIMEOUT = 5000;
	// 접근옵션
	private DBOption option;
	private String method;
	// 결과코드
	private int resultCode;
	// 콜백리스너
	private DBListener listener;

	// 생성자
	public ServerDB() {

	}

	@Override
	public void execute(DBOption option) {
		this.option = option;
		this.method = option.getMethod();
		this.listener = option.getListener();
		URL += option.getUrl();
		Mylog.i(TAG, "URL : " + URL);
		DBTask dbTask = new DBTask();
		dbTask.execute(option.getData());

	}

	// 작업 처리 쓰레드
	private class DBTask extends AsyncTask<Object, Void, String> {

		@Override
		protected String doInBackground(Object... data) {
			HttpClient client = getHttpClient();
			StringBuilder builder = null;
			
			String result = null;
			// 데이터베이스 요청할 메서드에 따라 다른 메소드를 실행한다.
			if (method.equals("get")) {
				builder = requestGet(client);
			} else if (method.equals("post")) {
				builder = requestPost(client, data[0]);
			}

			if (builder != null) {
				result = builder.toString();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Mylog.i(TAG, result);
			listener.onSendData(result);
		}

		// HttpClient를 생성하고 리턴한다.
		public HttpClient getHttpClient() {
			HttpClient client = new DefaultHttpClient();
			HttpParams HttpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(HttpParams, CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(HttpParams, SOCKET_TIMEOUT);
			return client;
		}

		// Get 처리 메소드
		public StringBuilder requestGet(HttpClient client) {
			StringBuilder builder = new StringBuilder();
			// 파라미터가 있는지 확인한다.
			if (option.getRequestParams() != null) {
				serReqeustParams();
			}
			HttpGet httpGet = new HttpGet(URL);

			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				resultCode = statusLine.getStatusCode();
				Mylog.i(TAG, "(Get) StatusCode = " + resultCode);
				if (resultCode == 200) {
					HttpEntity resultEntity = response.getEntity();
					InputStream result = resultEntity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(result));
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

		// Post 처리 메소드
		public StringBuilder requestPost(HttpClient client, Object data) {
			StringBuilder builder = new StringBuilder();

			// 파라미터가 있는경우와 없는경우를 나누어 준다.
			if (option.getRequestParams() != null) {
				serReqeustParams();
			}
			HttpPost httpPost = new HttpPost(URL);

			// 데이터를 셋팅한다.
			setDataToReqeustBody(httpPost, data);

			// HTTP 연결 을 실행합니다.
			try {
				HttpResponse respone = client.execute(httpPost);

				StatusLine statusLine = respone.getStatusLine();
				resultCode = statusLine.getStatusCode();
				Mylog.i(TAG, "(Post) StatusCode = " + resultCode);
				if (resultCode == 200) {
					HttpEntity resultEntity = respone.getEntity();
					InputStream result = resultEntity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(result));
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

		// 나중에 보안을 위해서 헤더파일에 정보가 추가될 예정이다.
		public void setHeader() {

		}

		// 요청에 파라미터를 추가합니다.
		public void serReqeustParams() {
			Map<String, String> map = option.getRequestParams();
			List<NameValuePair> params = new LinkedList<NameValuePair>();

			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				params.add(new BasicNameValuePair(key, map.get(key)));
				Mylog.i(TAG, "Reqeust Params : key = " + key + ", value = " + map.get(key));
			}
			String paramString = URLEncodedUtils.format(params, "utf-8");
			URL += "?" + paramString;

			Mylog.i(TAG, "URL with Params : " + URL);

		}

		// 데이터를 Reqeust Body에 셋팅합니다.
		public void setDataToReqeustBody(HttpPost httpPost, Object data) {
			Gson gson = new Gson();
			StringEntity stringEntity = null;
			try {
				stringEntity = new StringEntity(gson.toJson(data).toString(), "UTF-8");
				stringEntity.setContentType("application/json; charset=utf-8");
				stringEntity.setContentEncoding(new BasicHeader( HTTP.CONTENT_TYPE, "application/json; charset=utf-8"));
				httpPost.setEntity(stringEntity);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			Mylog.i(TAG, gson.toJson(data).toString());
			httpPost.setEntity(stringEntity);
		}
	}
}
