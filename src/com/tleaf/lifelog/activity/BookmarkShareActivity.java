package com.tleaf.lifelog.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.database.CouchDBLiteTask;
import com.tleaf.lifelog.util.database.DatabaseConnector;
import com.tleaf.lifelog.util.database.OnDataListener;

/**
 * Created by jangyoungjin on 8/10/14.
 */
public class BookmarkShareActivity extends Activity implements OnDataListener {
	private static final String TAG = "BOOKMARK ACTIVITY";
	private EditText dbname_edittext;
	private CouchDBLiteTask connector;
	
	/**
	 * 2014.08.18 MainActivity Life Cycle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Mylog.i(TAG, "Created MainActivity");
		setContentView(R.layout.activity_bookmark);
		dbname_edittext = (EditText) findViewById(R.id.dbname_edittext);
		connector = new CouchDBLiteTask(this);
		
		DatabaseConnector databaseConnector = new DatabaseConnector(this);
		databaseConnector.getDocument("bookmarks");
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

	@Override
	public void onSendData(String data) {
		// TODO Auto-generated method stub
		//제이슨 객체 뽑아쓰는 형식!
		Mylog.i(TAG,"data : " + data);
		try {
			JSONObject jsonObject = new JSONObject(data);
			JSONArray jsonArray =jsonObject.getJSONArray("data");
			List<Bookmark> list = new ArrayList<Bookmark>();
			for(int i = 0; i < jsonArray.length(); i++){
			    Bookmark bookmark = new Bookmark();
			    bookmark.setType(jsonArray.getJSONObject(i).getString("type"));
			    bookmark.setTitle(jsonArray.getJSONObject(i).getString("title"));
			    bookmark.setSiteUrl(jsonArray.getJSONObject(i).getString("siteUrl"));
			    bookmark.setDate(jsonArray.getJSONObject(i).getLong("date"));
			    list.add(bookmark);
			}
			for(Bookmark bookmark : list){
				Mylog.i(TAG, "bookmark title ; "+bookmark.getTitle());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
