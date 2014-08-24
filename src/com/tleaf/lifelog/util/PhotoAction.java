package com.tleaf.lifelog.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**이미지인지 판별하고 이미지의 처리를 진행한다*/
public class PhotoAction {
	private Intent intent;
	private Context context;
	private Uri imgUri;
	private String fileName;
	
	

	public PhotoAction(){
		
	}
	public PhotoAction(Intent intent,Context context){
		this.intent=intent;
		this.context=context;
	}
	
	public void run(){
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if ("text/plain".equals(type)) {
				setTextInfo(intent); // text 를 처리한다.
			} else if (type.startsWith("image/")) {
				Log.i("system", "getImage");
				setImgInfo(intent,context); // 단일 image 를 처리한다.
			}
		} else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
			if (type.startsWith("image/")) {
				Log.i("system", "getMultiImage");
				setMultiImgInfo(intent); // 여러 image 들을 처리한다.
			}
		} else {
		}
	}
	
	
	
	
	private void setImgInfo(Intent intent,Context context) {
		Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
		
		if (imageUri != null) {
			// 전달 받은 image 를 사용한다.

		}
		String scheme = imageUri.getScheme();
		String fileName = "";
		if (scheme.equals("file")) {
			fileName = imageUri.getLastPathSegment();
		} else if (scheme.equals("content")) {
			String[] proj = { MediaStore.Images.Media.TITLE };
			Cursor cursor = context.getContentResolver().query(imageUri, proj,
					null, null, null);

			if (cursor != null && cursor.getCount() != 0) {
				int columnIndex = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
				cursor.moveToFirst();
				fileName = cursor.getString(columnIndex);
			}
			if (cursor != null) {
				cursor.close();
			}
		}
		
		this.fileName = fileName;
		this.imgUri = imageUri;

	}
	
	private void setMultiImgInfo(Intent intent) {
		ArrayList<Uri> imageUris = intent
				.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
		if (imageUris != null) {
			// 전달받은 여러개의 image 를 사용한다.
		}
	}

	private void setTextInfo(Intent intent) {
		String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (sharedText != null) {
			// 전달 받은 text 를 UI 에 적용한다
		}
	}
	
	
	public Uri getImgUri() {
		return imgUri;
	}

	public void setImgUri(Uri imgUri) {
		this.imgUri = imgUri;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
