package com.tleaf.lifelog.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.util.Mylog;

/**
 * 
 * @author jangyoungjin 1. 내부 데이터베이스에 라이프로그를 저장. 2. 특정 타입의 라이프로그 요청 [ 내부 디비에
 *         저장되있으면 내부에서 가져온다 ]
 * 
 */
public class DatabaseConnector {
	private static final String TAG = "데이터베이스접근객체";
	private String userId = "youngjin"; // 나중에는 sharedpreference에서 가져온다.
	private OnDataListener listener;
	private String URL;

	public DatabaseConnector(OnDataListener listener) {
		this.listener = listener;
	}

	public void getDocument(String docType) {
		// 내부 디비에 해당 데이터의 유무에 따라서
		// 데이터 엑세스가 달라진다. [ 나중에 구현 ]
		URL = userId + "/" + docType;
		connectServerDB();
	}

	public void connectServerDB() {
		// 분석된 데이터를 요청할때
		// 최초 사용자 회원가입시 내부에서 데이터베이스 생성하기 위해서.
		ServerDBTask serverDBTask = new ServerDBTask(URL, "get", "", listener);
		serverDBTask.execute();
	}

	public void connectCouchDB() {
		// ?? ..
	}

	public void connectCouchDBLite() {
		// 모바일에서 생성되는 데이터 저장.
	}

}
