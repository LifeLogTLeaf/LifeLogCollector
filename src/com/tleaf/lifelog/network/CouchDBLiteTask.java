package com.tleaf.lifelog.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;

import com.couchbase.lite.AsyncTask;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;
import com.couchbase.lite.replicator.Replication.ChangeEvent;
import com.couchbase.lite.replicator.Replication.ChangeListener;
import com.couchbase.lite.replicator.Replication.ReplicationStatus;
import com.google.android.gms.common.api.a.d;
import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.util.Mylog;

/**
 * 2014.08.18
 * 
 * @author jangyoungjin 안드로이드 내부 카우치디비 접속을 도와주는 클래스 내부 데이터베이스는 타우치디비로 이루어져있습니다.
 */
public class CouchDBLiteTask implements DbAccessInterface {
	private static final String URL = "http://couchdb:dudwls@54.191.147.237:5984/";
	private static final String TAG = "카우치디비라이트작업";
	private ClassLoader classLoader;
	private Context context;

	/**
	 * 2014.08.18 by young 생성자
	 */
	public CouchDBLiteTask(Context context) {
		this.context = context;
		// 클래스 로더를 메인 액티비티것을 사용해줘야 하느데 이 부분은 잘 모르겠다..
		// 해결해야할 아주 중요한 부분이다.
		classLoader = context.getClassLoader();
		Thread.currentThread().setContextClassLoader(classLoader);
		Mylog.i(TAG, "[현재 쓰레드의 클래스로더] " + Thread.currentThread().getContextClassLoader().toString());

	}

	@Override
	public void getData(DbAccessOption option) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postData(DbAccessOption option, Lifelog lifelog) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "데이터 삽입중... ");
		Dbtask dbtask = new Dbtask();
		if (option != null) {
			dbtask.setMethod(option.getType());
			dbtask.setDbName(option.getDbName());
		}
		dbtask.execute(lifelog);
	}

	/**
	 * 
	 * @author jangyoungjin 쓰레드 작업.
	 */
	private class Dbtask extends android.os.AsyncTask<Lifelog, Void, String>
			implements ChangeListener {
		private Manager mManger; // The Manager is used later to access database
		private String method, dbName;

		public void setMethod(String method) {
			this.method = method;
		}

		public void setDbName(String dbName) {
			this.dbName = dbName;
		}

		@Override
		protected String doInBackground(Lifelog... data) {
			// TODO Auto-generated method stub
			String result = null;
			init();
			switch (method) {
			case "post":
				result = createDocument(data[0]);
				break;
			case "get":

				break;
			case "signup":
				result = startReplication();
				break;
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
				mManger.close();
			}
		}

		private void init() {
			try {
				mManger = new Manager(new AndroidContext(context),
						Manager.DEFAULT_OPTIONS);
				Mylog.i(TAG, "(쓰레드 init)카우치디비lite 메니저 생성");
			} catch (IOException e) {
				Mylog.i(TAG, "(쓰레드 init)카우치디비lite 메니저 생성 실패");
			}
		}

		/* 2014.08.18 by young 데이터베이스 생성. */
		private String createDatabase() {
			if (!Manager.isValidDatabaseName(dbName)) {
				Mylog.i(TAG, "(쓰레드)Bad Database Name");
				return "false";
			}

			Database database;
			try {
				database = mManger.getDatabase(dbName);
				Mylog.i(TAG, "(쓰레드)데이터 베이스가 생성되었습니다.");
				return "true";
			} catch (CouchbaseLiteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Mylog.i(TAG, "(쓰레드)데이터베이스 생성이 실패했습니다.");
				return "false";
			}
		}

		/* 2014.08.18 by young 도큐먼트 생성. */
		private String createDocument(Lifelog lifelog) {
			Map<String, Object> data = new HashMap<String, Object>();
			lifelog.setMap(data);

			Database database = null;
			try {

				database = mManger.getDatabase(dbName);
			} catch (CouchbaseLiteException e1) {
				e1.printStackTrace();
			}
			Document document = database.createDocument();

			try {
				document.putProperties(data);
				Mylog.i(TAG,
						"(쓰레드)생성한 데이터 : "
								+ String.valueOf(document.getProperties()));
				return "true";
			} catch (CouchbaseLiteException e) {
				e.printStackTrace();
				return "false";
			}

		}

		/**
		 * 2014.08.18 by young 레플리케이션 시작. 이슈 사항 많음.. 레플리케이션에 대해서는 나중에 !
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
			// 이슈
			// 데이터 변동이 일어나면 바로바로 복제를 하는데 문제는 네트워크사용량!
			// continuous를 해제해서 정책에 따라 복제를 해야 네트워크사용량 낭비가 줄어든다.
			// push.setContinuous(true);

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
