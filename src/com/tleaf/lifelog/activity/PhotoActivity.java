package com.tleaf.lifelog.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Photo;
import com.tleaf.lifelog.network.CouchDBConnector;
import com.tleaf.lifelog.util.PhotoAction;
import com.tleaf.lifelog.util.adapter.PhotoListAdapter;

public class PhotoActivity extends Activity {
	ImageView imgView;
	TextView txtView;
	ListView listView;
	PhotoListAdapter arrAdapter;
	ArrayList<String> arrList;

	PhotoAction shareAction;

	ArrayList<Photo> arrFileList;
	public final static String FINAL_DATE = "1408250101000";

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
			initWidget();
			// 업로드 되지 않은 파일 목록을 표시하는 과정
			arrFileList = getImagesInfo();

			arrAdapter = new PhotoListAdapter(this,
					R.layout.layout_photo_preview, arrFileList);

			listView.setAdapter(arrAdapter);

		}
	}

	public void dbConnecnt() {
		Photo lifeLog = new Photo();
		Uri imgUri = shareAction.getImgUri();
		String fileName = shareAction.getFileName();
		String imgPath = getPathFromUri(imgUri);
		lifeLog.setFileName(fileName);
		lifeLog.setImgPath(imgPath);
		CouchDBConnector couchDBTask = new CouchDBConnector("photo", "post", "insert-photo");
		couchDBTask.setContext(getApplicationContext());
		couchDBTask.execute(lifeLog);
	}

	public String getPathFromUri(Uri uri) {

		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		cursor.moveToNext();
		String path = cursor.getString(cursor.getColumnIndex("_data"));
		cursor.close();

		return path;
	}

	public void onClick(View view) {

		dbConnecnt();

	}

	/** 공유시에 활성화 해야하는 기능 */
	boolean isShare() {

		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if ((Intent.ACTION_SEND.equals(action) || Intent.ACTION_SEND_MULTIPLE
				.equals(action)) && type != null) {
			setContentView(R.layout.activity_share_main);
			shareAction = new PhotoAction(intent, getBaseContext());
			shareAction.run();
			return true;
		} else {
			// 다른 인텐트들을 처리한다. 예를들어 home 화면에서 시작된 것일 수 있다.
			// 그것도 아니라면 공유가 되지 않은 것으로 처리
		}
		return false;
	}

	/** 디바이스 내의 모든 이미지 정보를 불러온다 */
	public ArrayList<Photo> getImagesInfo() {
		ArrayList<Photo> arrFileList = new ArrayList<Photo>();
		Cursor mManagedCursor;

		mManagedCursor = getContentResolver().query(
				Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

		if (mManagedCursor != null) {
			mManagedCursor.moveToFirst();

			int nSize = mManagedCursor.getColumnCount();
			while (mManagedCursor.moveToNext()) {

				String data = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.DATA)); // 데이터 스트림.
																	// 파일의 경로

				String title = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.TITLE)); // 제목

				String date_taken = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.DATE_TAKEN)); // 촬영날짜.
																			// 1/1000초
																			// 단위

				String description = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.DESCRIPTION)); // Image에
				// 대한
				// 설명

				String is_private = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.IS_PRIVATE)); // 공개
				// 여부

				String latitude = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.LATITUDE)); // 위도

				String longitude = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.LONGITUDE)); // 경도

				String date_modified = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.DATE_MODIFIED)); // 최후
				// 갱신
				// 날짜.
				// 초단위

				String mime_type = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.MIME_TYPE)); // 마임
				// 타입, 이미지의 타입

				String size = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.SIZE)); // 파일의 크기

				/* 활용되어야 할 것들 */

				String bucket_display_name = mManagedCursor
						.getString(mManagedCursor
								.getColumnIndex(Images.ImageColumns.BUCKET_DISPLAY_NAME)); // 버킷의
				// 이름

				String bucket_id = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.BUCKET_ID)); // 버킷
				// ID

				String mini_thumb_magic = mManagedCursor
						.getString(mManagedCursor

						.getColumnIndex(Images.ImageColumns.MINI_THUMB_MAGIC)); // 작은
				// 썸네일

				String orientation = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.ORIENTATION)); // 사진의
				// 방향.
				// 0,
				// 90,
				// 180,
				// 270
				String picasa_id = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.PICASA_ID)); // 피카사에서
				// 매기는
				// ID
				String id = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns._ID)); // 레코드의 PK

				String display_name = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.DISPLAY_NAME)); // 파일
				// 표시명

				String date_added = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.DATE_ADDED)); // 추가
				// 날짜.
				// 초단위

				// 업데이트된 시간보다 이후의 파일이라면 업데이트 목록에 올림

				if (Long.parseLong(FINAL_DATE) < Long.parseLong(date_taken)) {
					Log.i("system", data);
					// BitmapFactory.Options opt = new BitmapFactory.Options();
					// opt.inSampleSize = 4;
					// Bitmap bm = BitmapFactory.decodeFile(data, opt);
					Photo photoData = new Photo();
					photoData.setFileName(title + ", " + date_taken);
					photoData.setImgPath(data);
					arrFileList.add(photoData);
				}
				// System.out.println("title"+title);
				// System.out.println("bucket_display_name"+bucket_display_name);
				// System.out.println("buckId"+bucket_id);
				// System.out.println("desc"+description);
				// System.out.println("공개여부 "+ is_private);
				// System.out.println("lat"+latitude);
				// System.out.println("lon"+longitude);
				// System.out.println("mini_thumb"+mini_thumb_magic);
				// System.out.println("id"+id);
				// System.out.println("size"+size);
				// System.out.println("mime"+mime_type);
				// System.out.println("disp_name"+display_name);
				// System.out.println("data_modi"+date_modified);
				// System.out.println("date_added"+date_added);

			}
		}
		return arrFileList;
	}

	void initWidget() {
		imgView = (ImageView) findViewById(R.id.imgFile);
		txtView = (TextView) findViewById(R.id.txtFileName);
		listView = (ListView) findViewById(R.id.list);
	}


}
