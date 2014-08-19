package com.tleaf.lifelog.util.database;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;
import com.couchbase.lite.replicator.Replication.ChangeEvent;
import com.couchbase.lite.replicator.Replication.ChangeListener;
import com.tleaf.lifelog.model.FacebookUserInfor;
import com.tleaf.lifelog.model.UserInfor;
import com.tleaf.lifelog.util.Mylog;

/**
 * 2014.08.18
 * 
 * @author jangyoungjin 안드로이드 내부 카우치디비 접속을 도와주는 클래스 내부 데이터베이스는 타우치디비로 이루어져있습니다.
 */
public class CouchDBLiteTask implements ChangeListener{
	private static final String TAG = "카우치디비Lite";
	private static final String URL = "http://couchdb:dudwls@54.191.147.237:5984/";
	private Manager mManger; // The Manager is used later to access database
	private Replication push; // For monitring replication

	/**
	 * 2014.08.18 by young 생성자
	 */
	public CouchDBLiteTask(Context context) {
		try {
			mManger = new Manager(new AndroidContext(context),
					Manager.DEFAULT_OPTIONS);
			Mylog.i(TAG, "Manager created");
		} catch (IOException e) {
			Mylog.i(TAG, "Cannot Create Manger Object");
		}
	}

	/**
	 * 2014.08.18 by young 데이터베이스 관련 연산자
	 */
	/* 2014.08.18 by young 데이터베이스 생성. */
	public boolean createDatabase(String dbName) {
		if (!Manager.isValidDatabaseName(dbName)) {
			Mylog.i(TAG, "Bad Database Name");
			return false;
		}

		Database database;
		try {
			database = mManger.getDatabase(dbName);
			Mylog.i(TAG, "Database created");
			return true;
		} catch (CouchbaseLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Mylog.i(TAG, "Cannot get database");
			return false;
		}
	}

	/* 2014.08.18 by young 데이터베이스 삭제 */
	public boolean deleteDatabase(String dbName) {
		if (!Manager.isValidDatabaseName(dbName)) {
			Mylog.i(TAG, "Bad Database Name");
			return false;
		}

		Database database;
		try {
			database = mManger.getDatabase(dbName);
			database.delete();
			Mylog.i(TAG, "Database deleted");
			return true;
		} catch (CouchbaseLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Mylog.i(TAG, "Cannot delete database");
			return false;
		}
	}

	/**
	 * 2014.08.18 by young 도큐먼트 관련 연산자
	 */
	/* 2014.08.18 by young 도큐먼트 생성 */
	public boolean createDocument(String dbName,
			com.tleaf.lifelog.model.Document lifelog) {
		Map<String, Object> data = new HashMap<String, Object>();
		// 시간 추가 해줘야한다.
		lifelog.setDate(2014082030);
		lifelog.setMap(data);
		
		Database database = null;
		try {
			database = mManger.getDatabase(dbName);
		} catch (CouchbaseLiteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document document = database.createDocument();

		try {
			document.putProperties(data);
			Mylog.i(TAG, String.valueOf(document.getProperties()));
			return true;
		} catch (CouchbaseLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/* 2014.08.18 by young 도큐먼트 읽기 */
	public String retrieveDocument(String dbName, String docId) {
		Database database = null;
		try {
			database = mManger.getDatabase(dbName);
		} catch (CouchbaseLiteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Document document = database.getDocument(docId);
		Mylog.i(TAG, "data : " + String.valueOf(document.getProperties()));
		return String.valueOf(document.getProperties());
	}

	/* 2014.08.18 by young 도큐먼트 업데이트 */
	public void updateDocument(String dbName, String docId) {
		// ....
	}

	/* 2014.08.18 by young 도큐먼트 생성 */
	public boolean deleteDocument(String dbName, String docId) {
		Database database = null;
		try {
			database = mManger.getDatabase(dbName);
		} catch (CouchbaseLiteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Document document = database.getDocument(docId);

		try {
			document.delete();
			Mylog.i(TAG, "deletion status = " + document.isDeleted());
			return true;
		} catch (CouchbaseLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Mylog.i(TAG, "cannot delete");
			return false;
		}
	}

	/**
	 * 2014.08.18 by young 레플리케이션 시작.
	 */
	public void startReplication(String dbName) {
		Database database = null;
		try {
			database = mManger.getDatabase(dbName);
		} catch (CouchbaseLiteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		URL url = null;
		try {
			url = new URL(URL+dbName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Replication push = database.createPushReplication(url);
		//이슈
		//데이터 변동이 일어나면 바로바로 복제를 하는데 문제는 네트워크사용량!
		//continuous를 해제해서 정책에 따라 복제를 해야 네트워크사용량 낭비가 줄어든다.
		push.setContinuous(true); 

		push.addChangeListener(this);
		push.start();
		this.push = push;
	}
	
	/**
	 * 2014.08.18 by young 레플리케이션 시작.
	 */
	public void stopReplication() {
		if(push !=null){
			push.stop();
		}
	}

	@Override
	public void changed(ChangeEvent event) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "replication status : " + event.getSource().getStatus());
	}

}
