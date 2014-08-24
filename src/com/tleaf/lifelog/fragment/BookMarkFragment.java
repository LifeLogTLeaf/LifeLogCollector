package com.tleaf.lifelog.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.model.Call;
import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.model.Location;
import com.tleaf.lifelog.model.Photo;
import com.tleaf.lifelog.model.Sms;
import com.tleaf.lifelog.network.DbConnector;
import com.tleaf.lifelog.network.OnDataListener;
import com.tleaf.lifelog.util.Mylog;

public class BookMarkFragment extends Fragment implements OnDataListener {
	private static final String TAG = "북마크 프래그먼트";
	private Context mContext;
	private BookMarkFragment fragment;
	private String DbName = "jin";
	private EditText get_edittext;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
		Mylog.i("온어테치", "컨텍스트 : " + mContext.getApplicationContext().toString());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_bookmark, container,
				false);
		fragment = this;

		rootView.findViewById(R.id.replication_start_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						DbConnector db = new DbConnector(fragment, mContext
								.getApplicationContext());
						db.postData(DbName, "signup");
					}
				});

		rootView.findViewById(R.id.createdoc_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						generateRandomData();
					}
				});

		rootView.findViewById(R.id.getdoc_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						getLifelog();
					}
				});
		
		get_edittext = (EditText) rootView.findViewById(R.id.get_edittext);

		return rootView;
	}

	public void generateRandomData() {
		DbConnector db = new DbConnector(fragment,
				mContext.getApplicationContext());
		Random random = new Random();
		int i = random.nextInt(6);
		switch (i) {
		case 0:
			Bookmark bookmark = new Bookmark();
			bookmark.setTitle("짜장면");
			bookmark.setType("bookmark");
			bookmark.setUrl("www.korea.com");
			db.postData(DbName, bookmark);
			break;
		case 1:
			Sms sms = new Sms();
			sms.setType("sms");
			sms.setAddress("01031107800");
			sms.setBody("안녕 나는 김연아야 사랑해");
			sms.setDate(System.currentTimeMillis());
			db.postData(DbName, sms);
			break;
		case 2:
			Photo photo = new Photo();
			photo.setType("photo");
			photo.setFileName("화끈한이미지");
			photo.setImgPath("c/file");
			db.postData(DbName, photo);
			break;
		case 3:
			Call call = new Call();
			call.setType("call");
			call.setDate("오늘");
			call.setNumber("01031107800");
			call.setName("오카미사");
			db.postData(DbName, call);
			break;
		case 4:
			Location location = new Location();
			location.setType("location");
			location.setLatitude(127);
			location.setLongitude(37);
			db.postData(DbName, location);

			break;
		}
	}

	public void getLifelog() {
		DbConnector db = new DbConnector(this, mContext.getApplicationContext());
		db.getData(DbName, get_edittext.getText().toString());
	}

	@Override
	public void onSendData(String data) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// TODO Auto-generated method stub
		// Mylog.i(TAG, data);
		try {
			JSONObject jsonObject = new JSONObject(data);
			Mylog.i(TAG, "version : " + jsonObject.getString("count"));
			Mylog.i(TAG, "count : " + jsonObject.getString("version"));
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			List<Lifelog> list = new ArrayList<Lifelog>();
			for (int i = 0; i < jsonArray.length(); i++) {

				list.add(gson.fromJson(jsonArray.getString(i), Bookmark.class));
			}

			for (Lifelog lifelog : list) {
				Mylog.i(TAG, "type : " + lifelog.getType());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
