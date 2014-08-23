package com.tleaf.lifelog.network;

import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.util.Mylog;

import android.content.Context;

/**
 * 
 * @author jangyoungjin 1. 내부 데이터베이스에 라이프로그를 저장. 2. 특정 타입의 라이프로그 요청 [ 내부 디비에
 *         저장되있으면 내부에서 가져온다 ]
 * 
 */
public class DbConnector {
	private static final String TAG = "데이터베이스접근객체";
	private OnDataListener listener;
	private DbAccessInterface db;
	private DbAccessOption option;
	private Context context;
	private String URL, dbName, dataName;

	public DbConnector(OnDataListener listener, Context context) {
		this.listener = listener;
		this.context = context;
	}

	/**
	 * 
	 * @param dataName
	 */
	public void getData(String dataName) {
		if (checkNetwork()) {
		} else {
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean checkNetwork() {
		return true;
	}


	/**
	 * 
	 * @param dbName
	 * @param type
	 */
	public void postData(String dbName, String requestName) {
		Mylog.i(TAG, "레플리케이션중.");
		if (requestName.equals("signup")) {
			db = new CouchDBLiteTask(context);
			option = new DbAccessOption();
			option.setType("signup");
			option.setDbName(dbName);
			db.postData(option, null);
		} else {
			
		}
	}

	/**
	 * 
	 * @param dbName
	 * @param document
	 */
	public void postData(String dbName, Lifelog document) {
		Mylog.i(TAG, "데이터를 삽입 중");
		db = new CouchDBLiteTask(context);
		option = new DbAccessOption();
		option.setType("post");
		option.setDbName(dbName);
		db.postData(option, document);
	}

}
