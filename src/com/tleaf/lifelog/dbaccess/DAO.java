package com.tleaf.lifelog.dbaccess;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.tleaf.lifelog.dbaccess.db.DBInterface;
import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.util.Mylog;

/**
 * 2014.08.27 데이터베이스 접근 객체.
 * 
 * @author jangyoungjin
 * 
 */
public class DAO {
	private static final String TAG = "DAO";
	private DBListener listner;
	private DBInterface db;
	private Context context;
	private DBOption option;

	/* 생성자 */
	public DAO(DBListener listener, Context context) {
		option = new DBOption();
		option.setContext(context);
		option.setListener(listener);
	}

	/* GET DATA */
	public void getData(String dbName, String url, String dest) {
		Mylog.i(TAG, "DB Access To : "+dest); 
		db = DBFactory.createDB(dest);
		option.setDbName(dbName);
		option.setUrl(url);
		option.setMethod("get");
		db.execute(option);
	}

	/* GET DATA With Param */
	public void getData(String dbName, String url, Map<String, String> params, String dest) {
		Mylog.i(TAG, "DB Access To : "+dest); 
		db = DBFactory.createDB(dest);
		option.setDbName(dbName);
		option.setUrl(url);
		option.setRequestParams(params);
		option.setMethod("get");
		db.execute(option);
	}

	/* POST DATA */
	public void postData(String dbName, Lifelog data, String dest) {
		Mylog.i(TAG, "DB Access To : "+dest); 
		db = DBFactory.createDB(dest);
		option.setDbName(dbName);
		option.setLifelog(data);
		option.setMethod("post");
		db.execute(option);
	}
	
	//서버로 리스트 데이터 전
	public void postData(String dbName, List listData, String dest, String url) {
		Mylog.i(TAG, "DB Access To : "+dest); 
		db = DBFactory.createDB(dest);
		option.setDbName(dbName);
		option.setUrl(url);
		option.setListData(listData);
		option.setMethod("post");
		db.execute(option);
	}
	
	// 사용자 로그인, 서버로 데이터 전송
	public void postData(String dbName, Object data, String dest, String url) {
		Mylog.i(TAG, "DB Access To : "+dest); 
		db = DBFactory.createDB(dest);
		option.setDbName(dbName);
		option.setUrl(url);
		option.setData(data);
		option.setMethod("post");
		db.execute(option);
	}

	/* START REPLICATION */
	public void startReplication(String dbName) {
		Mylog.i(TAG, "DB Access To : touch" ); 
		db = DBFactory.createDB("touch");
		option.setDbName(dbName);
		option.setMethod("replication");
		db.execute(option);
	}

}
