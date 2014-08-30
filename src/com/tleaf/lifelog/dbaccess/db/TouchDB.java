package com.tleaf.lifelog.dbaccess.db;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;
import com.couchbase.lite.replicator.Replication.ChangeEvent;
import com.couchbase.lite.replicator.Replication.ChangeListener;
import com.couchbase.lite.replicator.Replication.ReplicationStatus;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.tleaf.lifelog.dbaccess.DBListener;
import com.tleaf.lifelog.dbaccess.DBOption;
import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.util.Mylog;

public class TouchDB implements DBInterface {
	private static final String TAG = "카우치디비라이트작업";
	private static final String URL = "http://couchdb:dudwls@54.191.147.237:5984/";
	// 접근옵션
	private DBOption option;
	private String method;
	private String dbName;
	private String url;
	// 클래스 로더 
	private ClassLoader classLoader;
	// 액티비티 컨텍스트
	private Context context;
	// 콜백리스너
	private DBListener listener;
	// 사용자이름 ( 나중에 Preference에서 뽑는다 )
	private String userName = "jYoung";

	
	

	@Override
	public void execute(DBOption option) {
		// TODO Auto-generated method stub
		//데이터 베이스를 작업을 하기 위한 초기 설정 값들을 셋팅해준다.
		this.option = option;
		this.method = option.getMethod();
		this.dbName = option.getDbName();
		this.context = option.getContext();
		this.listener = option.getListener();
		this.url = option.getUrl();
		setClassloader();
		
		DBtask dbTask = new DBtask();
		dbTask.execute(option.getLifelog());
	}
	
	// 터치디비를 사용하기 위해서 액티비티 클래스로더를 로딩한다.
	public void setClassloader() {
		// 클래스 로더를 메인 액티비티것을 사용해줘야 하느데 이 부분은 잘 모르겠다. 해결해야할 아주 중요한 부분이다.
		classLoader = context.getClassLoader();
		Thread.currentThread().setContextClassLoader(classLoader);
		Mylog.i(TAG, "( 현재 쓰레드의 클래스로더 ) " + Thread.currentThread().getContextClassLoader().toString());

	}

	// 작업하는 테스크
	private class DBtask extends android.os.AsyncTask<Lifelog, Void, String> implements ChangeListener {
		private Manager mManger; // The Manager is used later to access database

		@Override
		protected String doInBackground(Lifelog... data) {
			// TODO Auto-generated method stub
			String result = "false";

			// 터치 디비를 초기화 시켜줍니다. 
			init();

			if (method.equals("post")) {
				result = createDocument(data[0]);
			} else if (method.equals("get")) {
			
			} else if (method.equals("replication")) {
				result = startReplication();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Mylog.i(TAG, "(쓰레드 onPost) 작업완료 : " + result);
			if (result.equals("true")) {
				Mylog.i(TAG, "리소스 close");
				listener.onSendData(result);
				mManger.close();
			}
		}

		// 터치디비 초기화 메서드 
		private void init() {
			try {
				mManger = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
				Mylog.i(TAG, "카우치디비lite 메니저 생성");
			} catch (IOException e) {
				Mylog.i(TAG, "카우치디비lite 메니저 생성 실패");
			}
		}

		/** 2014.08.18 by young 도큐먼트 생성. 
		 * 2014.8.27 by susu LifeLog 클래스에 있는 setMap 메소드 대신 mapper 사용
		 */
		private String createDocument(Lifelog lifelog) {
			Map<String, Object> data = convertLifelogToMap(lifelog);
			Database database = null;
			try {

				database = mManger.getDatabase(dbName);
			} catch (CouchbaseLiteException e1) {
				e1.printStackTrace();
			}
			Document document = database.getDocument(lifelog.genId(userName));

			try {
				document.putProperties(data);
				Mylog.i(TAG,
						"[TASK] 생성한 데이터 : "
								+ String.valueOf(document.getProperties()));
				return "true";
			} catch (CouchbaseLiteException e) {
				e.printStackTrace();
				return "false";
			}

		}
		
		// 라이프로그 객체를 맵객체로 변환해주는 메소드
		private HashMap<String, Object> convertLifelogToMap(Lifelog lifelog){
			HashMap<String, Object> data = null;
			Gson gson = new Gson();
			String jsonString = gson.toJson(lifelog);
			ObjectMapper mapper = new ObjectMapper();
			try {
				data = mapper.readValue(jsonString, new TypeReference<HashMap<String , Object>>() {});
			} catch (JsonParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (JsonMappingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return data;
		}

		/**
		 * 2014.08.18 by young 
		 */
		public String startReplication() {
			Database database = null;
			try {
				database = mManger.getDatabase(dbName);
			} catch (CouchbaseLiteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			URL url = null;
			try {
				url = new URL(URL + dbName);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Replication push = database.createPushReplication(url);
			
			push.addChangeListener(this);
			push.start();
			return "success";
		}

		@Override
		public void changed(ChangeEvent event) {
			// TODO Auto-generated method stub
			Mylog.i(TAG, "레플리케이션 상태 : " + event.getSource().getStatus());
			if (event.getSource().getStatus()
					.equals(ReplicationStatus.REPLICATION_IDLE)) {
				Mylog.i(TAG, "IDLE");
				mManger.close();
			}

		}

	}

}
