package com.tleaf.lifelog.fragment;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.dbaccess.DAO;
import com.tleaf.lifelog.dbaccess.DBListener;
import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.model.FacebookUserInfo;
import com.tleaf.lifelog.model.UserInfo;
import com.tleaf.lifelog.util.Mylog;

public class BookMarkFragment extends Fragment implements DBListener {
	private static final String TAG = "북마크 프래그먼트";
	private Context mContext;
	private BookMarkFragment fragment;
	private String DBNAME = "jin";
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
		View rootView = inflater.inflate(R.layout.fragment_fbpost, container,
				false);
		fragment = this;

		rootView.findViewById(R.id.replication_start_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						replication();
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
		
		rootView.findViewById(R.id.facebook_login_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						login();
					}
				});

		return rootView;
	}

	public void generateRandomData() {
		Bookmark bookmark = new Bookmark();
		bookmark.setTitle("짜장면");
		bookmark.setlogType("bookmark");
		bookmark.setUrl("www.korea.com");
		DAO db = new DAO(this, getActivity().getApplicationContext());
		db.postData("jin", bookmark, "touch");
	}

	public void getLifelog() {
		DAO db = new DAO(this, getActivity().getApplicationContext());
		HashMap<String, String> param = new HashMap<>();
		param.put("userid", "jin");
		db.getData("jin", "lifelogs", param, "server");
	}
	
	public void login() {
		DAO db = new DAO(this, getActivity().getApplicationContext());
		HashMap<String, String> param = new HashMap<>();
		param.put("userid", "jin");
		
		FacebookUserInfo user = new FacebookUserInfo();
		user.setFacebookId("안녕하세요 ");
		user.setFacebookLastUpdateDate("나는 ");
		user.setFacebookLastpostDate("한글입니다..^^");
		
		UserInfo user2 = new UserInfo();
		user2.setUserName("user2");
		user2.setGender("fuck");
		user2.setUserFacebookUserInfo(user);
		db.postData("jin", user2, "server", "facebooklogin");
	}
	
	public void replication(){
		DAO db = new DAO(this, getActivity().getApplicationContext());
		db.startReplication("jin");
	}

	@Override
	public void onSendData(String data) {
		Mylog.i(TAG, data);

	}

}
