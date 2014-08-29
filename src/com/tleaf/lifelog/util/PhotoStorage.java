package com.tleaf.lifelog.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images;
import android.util.Log;

import com.tleaf.lifelog.model.Photo;

public class PhotoStorage {
	Context context;
	/**데이터 변경조건: 갤러리 파일에서 while을 돌면서 
	 * 가장 최근의 사진시간을 감지(String recentDate) 
	 * 최종적으로 나온 최근 시간을 FINAL_DATE에 저장 */
	public static String FINAL_DATE = "1408640101000";
	//while을 돌면서 사진의 촬영시간을 저장한다  
	String recentDate = "0";

	public PhotoStorage(Context context) {
		this.context = context;
	}

	/** 디바이스 내의 모든 이미지 정보를 불러온다 */
	public ArrayList<Photo> getImagesInfo() {
		ArrayList<Photo> arrFileList = new ArrayList<Photo>();
		Cursor mManagedCursor;
		
		
		mManagedCursor = context.getContentResolver().query(
				Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

		if (mManagedCursor != null) {
			
			mManagedCursor.moveToFirst();

			int nSize = mManagedCursor.getColumnCount();
			while (mManagedCursor.moveToNext()) {
				// 촬영날짜.1/1000초단위
				String date_taken = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.DATE_TAKEN));

				// 업로드 된 시간보다 이전의 파일이라면 더이상 탐색하지 않고 스킵한다
//				while (Long.parseLong(FINAL_DATE) > Long.parseLong(date_taken)) {
//					System.out.println(Long.parseLong(FINAL_DATE)-Long.parseLong(date_taken));
//					//스킵 중에 디렉토리의 마지막까지 왔다면 종료
//					if (mManagedCursor.isLast()) {
//						System.out.println("break");
//						break;
//					}
//					//아니라면 계속해서 뒤로 넘기면서 값을 확인
//					else {
//						
//						mManagedCursor.moveToNext();
//						// 촬영날짜. 1/1000초 단위
//						date_taken = mManagedCursor
//								.getString(mManagedCursor
//										.getColumnIndex(Images.ImageColumns.DATE_TAKEN));
//					}
//				}

				String mini_thumb_magic = mManagedCursor
						.getString(mManagedCursor

						.getColumnIndex(Images.ImageColumns.MINI_THUMB_MAGIC)); // 작은
				// 썸네일

				// System.out.println("thumb : " + mini_thumb_magic);
				String data = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.DATA)); // 데이터 스트림.
																	// 파일의 경로

				String title = mManagedCursor.getString(mManagedCursor
						.getColumnIndex(Images.ImageColumns.TITLE)); // 제목

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
					if (Long.parseLong(recentDate) < Long.parseLong(date_taken)) {
						recentDate = date_taken;
					}
					

					// BitmapFactory.Options opt = new BitmapFactory.Options();
					// opt.inSampleSize = 4;
					// Bitmap bm = BitmapFactory.decodeFile(data, opt);
					Photo photoData = new Photo();
					photoData.setFileName(title);
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

		if (Long.parseLong(FINAL_DATE) < Long.parseLong(recentDate)) {
			Mylog.i("photo","최종시간 변경 : " + recentDate);
			FINAL_DATE = recentDate;
		}
		mManagedCursor.close();
		return arrFileList;
	}
}
