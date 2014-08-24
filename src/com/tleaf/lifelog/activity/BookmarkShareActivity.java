package com.tleaf.lifelog.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.network.CouchDBLiteConnector;

/**O
 * Created by jangyoungjin on 8/10/14.
 */
public class BookmarkShareActivity extends Activity {
	private static final String TAG = "BOOKMARK ACTIVITY";
	private EditText dbname_edittext;
	private CouchDBLiteConnector connector;
	
	/**
	 * 2014.08.18 MainActivity Life Cycle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Mylog.i(TAG, "Created MainActivity");
		setContentView(R.layout.activity_bookmark);
		dbname_edittext = (EditText) findViewById(R.id.dbname_edittext);
		connector = new CouchDBLiteConnector(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	
	/**
	 * 2014.08.18 Button click Listener
	 */
	/* 디비 생성 */
	public void onDBCreate(View view){
		connector.createDatabase(dbname_edittext.getText().toString());
	}
	
	/* 디비 삭제 */
	public void onDBDelete(View view){
		connector.deleteDatabase(dbname_edittext.getText().toString());		
	}
		
	/* 도큐먼트생성 */
	public void onDocCreate(View view){
		Bookmark bookmark = new Bookmark();
		bookmark.setTitle("짜장면");
		bookmark.setType("bookmark");
		bookmark.setSiteUrl("www.korea.com");
		bookmark.setId("young20141011");
		connector.createDocument(dbname_edittext.getText().toString(), bookmark);
	}
	
	/* 레플리케이션 시작 */
	public void onReplicationStart(View view){
		connector.startReplication(dbname_edittext.getText().toString());
	}
	
	
	/* 레플리케이션 중 */
	public void onReplicationStop(View view){
		connector.stopReplication();
	}
	
	
}
