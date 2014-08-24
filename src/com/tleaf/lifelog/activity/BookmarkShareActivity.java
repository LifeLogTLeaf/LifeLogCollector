package com.tleaf.lifelog.activity;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.EditText;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.network.DbConnector;
import com.tleaf.lifelog.network.OnDataListener;
import com.tleaf.lifelog.util.Mylog;

/**O
 * Created by jangyoungjin on 8/10/14.
 */
public class BookmarkShareActivity extends Activity implements OnDataListener {
	private static final String TAG = "북마크 엑티비티";
	private EditText dbname_edittext;
	private String title, url, time, dbName;

	/**
	 * 2014.08.18 MainActivity Life Cycle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookmarkshare);

		// 테스트
		dbName = "jin";
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
	public void onSave(View view) {
		getBrowerHistory();
	}

	/**
	 * 브라우저 히스토리를 읽어들인다.
	 */
	private void getBrowerHistory() {
		Date date;
		int foundRecord = 0;
		Uri CHROME_URI = Uri
				.parse("content://com.android.chrome.browser/bookmarks");

		// 프로젝션
		String[] projection = new String[] { Browser.BookmarkColumns.DATE,
				Browser.BookmarkColumns.TITLE, Browser.BookmarkColumns.URL };

		// 셀렉트문. ( Where )
		String selection = Browser.BookmarkColumns.BOOKMARK + " = 0 ";

		// 기본 브라우저용.
		Cursor cursor = getContentResolver().query(Browser.BOOKMARKS_URI,
				projection, selection, null, null);

		// 크롬북 브라우저용.
		// Cursor cursor = getContentResolver().query(CHROME_URI,
		// projection, selection, null, null);

		cursor.moveToLast();
		time = cursor.getString(cursor
				.getColumnIndex(Browser.BookmarkColumns.DATE));
		title = cursor.getString(cursor
				.getColumnIndex(Browser.BookmarkColumns.TITLE));
		url = cursor.getString(cursor
				.getColumnIndex(Browser.BookmarkColumns.URL));
		Mylog.i(TAG, " title = " + title + ", URL = " + url + ", DATE = "
				+ time);
		cursor.close();

		Bookmark bookmark = new Bookmark();
		bookmark.setUrl(url);
		bookmark.setTitle(title);
		bookmark.setType("bookmakr");
		DbConnector db = new DbConnector(this, getApplicationContext());
		db.postData(dbName, bookmark);
	}

	@Override
	public void onSendData(String data) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "status : " + data);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss(); // 닫기
				finish();
			}
		});
		alert.setMessage("북마크가 성공적으로 완료되었습니다.");
		alert.show();
	}

}
