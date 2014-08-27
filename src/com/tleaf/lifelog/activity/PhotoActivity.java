package com.tleaf.lifelog.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.listAdapter.PhotoListAdapter;
import com.tleaf.lifelog.model.Photo;
import com.tleaf.lifelog.network.CouchDBConnector;
import com.tleaf.lifelog.network.CouchDBTask;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.PhotoAction;
import com.tleaf.lifelog.util.PhotoStorage;

public class PhotoActivity extends Activity {
	ImageView imgView;
	TextView txtView;
	ListView listView;
	PhotoListAdapter arrAdapter;
	ArrayList<String> arrList;

	ImageView img;
	PhotoAction shareAction;

	ArrayList<Photo> arrFileList;
	PhotoStorage photoStorage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		// 공유를 통해 접속됐다면.
		if (isShare()) {
			initWidget();
			txtView.setText(shareAction.getFileName());
			imgView.setImageURI(shareAction.getImgUri());

		} else {
//			startService(new Intent("com.tleaf.lifelog.service.UploaderService"));
			initWidget();
//			 업로드 되지 않은 파일 목록을 표시하는 과정
			photoStorage = new PhotoStorage(getApplication());
			arrFileList = photoStorage.getImagesInfo();

			arrAdapter = new PhotoListAdapter(this,
					R.layout.layout_photo_preview, arrFileList);

			listView.setAdapter(arrAdapter);

		}
	}

	/** 공유시에 활성화 해야하는 기능 */
	boolean isShare() {

		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if ((Intent.ACTION_SEND.equals(action) || Intent.ACTION_SEND_MULTIPLE
				.equals(action)) && type != null) {
			setContentView(R.layout.activity_photo_share);
			shareAction = new PhotoAction(intent, getBaseContext());
			shareAction.run();
			return true;
		} else {
			// 다른 인텐트들을 처리한다. 예를들어 home 화면에서 시작된 것일 수 있다.
			// 그것도 아니라면 공유가 되지 않은 것으로 처리
		}
		return false;
	}

	void initWidget() {
		imgView = (ImageView) findViewById(R.id.img);
		txtView = (TextView) findViewById(R.id.txtFileName);
		listView = (ListView) findViewById(R.id.list);
	}

}
