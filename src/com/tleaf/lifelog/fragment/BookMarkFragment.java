package com.tleaf.lifelog.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.network.DbConnector;
import com.tleaf.lifelog.network.OnDataListener;
import com.tleaf.lifelog.util.Mylog;

public class BookMarkFragment extends Fragment implements OnDataListener {
	private Context mContext;
	private BookMarkFragment fragment;

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
						db.postData("young", "signup");
					}
				});

		rootView.findViewById(R.id.createdoc_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						DbConnector db = new DbConnector(fragment, mContext
								.getApplicationContext());
						Bookmark bookmark = new Bookmark();
						bookmark.setTitle("짜장면");
						bookmark.setType("bookmark");
						bookmark.setSiteUrl("www.korea.com");
						db.postData("young", bookmark);
					}
				});

		return rootView;
	}

	@Override
	public void onSendData(String data) {
		// TODO Auto-generated method stub

	}
}