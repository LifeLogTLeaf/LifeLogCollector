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
public class DbConnector implements OnDataListener {
	private static final String TAG = "데이터베이스접근객체";
	private OnDataListener listener;
	private DbAccessInterface db;
	private DbAccessOption option;
	private Context context;

	public DbConnector(OnDataListener listener, Context context) {
		this.listener = listener;
		this.context = context;
	}

	public void getData(String dbName,String requestName) {
		//네트워크 셋팅이 추가될예정이다.
		db = DbTaskFactory.createTask("serverTask");
		option = new DbAccessOption();
		option.setContext(context);
		option.setDbName(dbName);
		option.setDbType("serverTask");
		option.setRequestMethod("get");
		option.setUrl(requestName);
		option.setListener(this);
		db.getData(option);
	}
	
	public void getData(String dataName,String requestName, boolean isWifi) {
		if(isWifi){
			
		}else {
			
		}
	}


	public void postData(String dbName, Lifelog lifelog) {
		db = DbTaskFactory.createTask("CouchDBLiteTask");
		option = new DbAccessOption();
		option.setContext(context);
		option.setDbName(dbName);
		option.setDbType("CouchDBLiteTask");
		option.setRequestMethod("post");
		option.setListener(this);
		db.postData(option, lifelog);
	}
	
	public void startReplication(String dbName){
		db = DbTaskFactory.createTask("CouchDBLiteTask");
		option = new DbAccessOption();
		option.setDbName(dbName);
		option.setContext(context);
		option.setDbType("CouchDBLiteTask");
		option.setRequestMethod("replication");
		option.setListener(this);
		db.postData(option, null);
	}

	@Override
	public void onSendData(String data) {
		// TODO Auto-generated method stub
		listener.onSendData(data);
	}

	
}
