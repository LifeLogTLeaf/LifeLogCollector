package com.tleaf.lifelog.fragment;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.listAdapter.CallListAdapter;
import com.tleaf.lifelog.log.MyCallLog;
import com.tleaf.lifelog.model.Call;
import com.tleaf.lifelog.pkg.FragmentListener;
public class CallFragment extends Fragment { 

	private Context mContext;
	private ArrayList<Call> arItem = null;
	private ListView lv;
	private CallListAdapter mAdapter = null;
	private int pos = -1;

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
		View rootView = inflater.inflate(R.layout.fragment_list, container, false);
		Log.e("first onCreateView", "");

		MyCallLog cl= new MyCallLog(mContext);
		arItem = cl.collectCall();
		mAdapter = new CallListAdapter(mContext, R.layout.item_call, arItem);

		lv = (ListView) rootView.findViewById(R.id.list);
		lv.setAdapter(mAdapter);
		lv.setOnItemLongClickListener(mItemLongClickListener);

		return rootView;
	}

	private AdapterView.OnItemLongClickListener mItemLongClickListener = new AdapterView.OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			registerForContextMenu(view);
			pos = position;
			return false;
		}

	};

	public void onCreateContextMenu(android.view.ContextMenu menu, View v, 
			android.view.ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(0, 1, 0, "삭제하기");
		menu.add(0, 2, 0, "지도보기");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			break;
		case 2:
		}
		return true;
	}

}