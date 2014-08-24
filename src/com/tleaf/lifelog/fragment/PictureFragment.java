package com.tleaf.lifelog.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony.Sms;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.activity.PhotoActivity;
import com.tleaf.lifelog.db.DataManager;
import com.tleaf.lifelog.listAdapter.SmsListAdapter;
import com.tleaf.lifelog.pkg.FragmentListener;
public class PictureFragment extends Fragment { 

	private Context mContext;
	private FragmentListener fListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
		fListener = (FragmentListener)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_picture, container, false);
		Log.e("pic onCreateView", "");

		return rootView;
	}
	
	/**
	 *	메인 액티비티에서 발생하는 버튼 터치에 대한 이벤트 리스너 입니다. 
	 */
	public void onClick(View v) {
		if (v.getId() == R.id.btn_photo) {
			Intent intent = new Intent(mContext, PhotoActivity.class);
			startActivity(intent);
		}
	}

}