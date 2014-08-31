package com.tleaf.lifelog.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.listAdapter.PhotoListAdapter;
import com.tleaf.lifelog.listAdapter.SmsListAdapter;
import com.tleaf.lifelog.model.Photo;
import com.tleaf.lifelog.pkg.FragmentListener;
import com.tleaf.lifelog.util.PhotoAction;
import com.tleaf.lifelog.util.PhotoStorage;
public class PhotoFragment extends Fragment { 

	private Context mContext;
	private SmsListAdapter mAdapter = null;
	private int pos = -1;

	private FragmentListener fListener;
	
	ImageView imgView;
	TextView txtView;
	ListView listView;
	PhotoListAdapter arrAdapter;
	ArrayList<String> arrList;

	ImageView img;
	PhotoAction shareAction;
	View rootView;
	ArrayList<Photo> arrFileList;
	PhotoStorage photoStorage;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
		fListener = (FragmentListener)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_photo, container, false);
		Log.e("first onCreateView", "");
		
//		mContext.startService(new Intent("com.tleaf.lifelog.service.UploaderService"));
		initWidget();
//		 업로드 되지 않은 파일 목록을 표시하는 과정
		photoStorage = new PhotoStorage(mContext);
		arrFileList = photoStorage.getImagesInfo();

		arrAdapter = new PhotoListAdapter(mContext,
				R.layout.layout_photo_preview, arrFileList);

		listView.setAdapter(arrAdapter);
		return rootView;
	}
	
	void initWidget() {
		imgView = (ImageView) rootView.findViewById(R.id.img);
		txtView = (TextView) rootView.findViewById(R.id.txtFileName);
		listView = (ListView) rootView.findViewById(R.id.list);
	}




}