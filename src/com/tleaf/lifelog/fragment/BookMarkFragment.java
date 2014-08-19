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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.activity.MainActivity_;
import com.tleaf.lifelog.db.DataManager;
import com.tleaf.lifelog.listAdapter.SmsListAdapter;
import com.tleaf.lifelog.pkg.FragmentListener;
public class BookMarkFragment extends Fragment { 

	private Context mContext;
	private ArrayList<Sms> arItem = null;
	private ListView lv;
	private SmsListAdapter mAdapter = null;
	private DataManager dataManager;
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

		dataManager = new DataManager(mContext);
		arItem = new ArrayList<Sms>();
		//서버에서 받아오기
		//arItem = dataManager.getSmsList(); 		
//		mAdapter = new SmsListAdapter(mContext, R.layout.item_sms, arItem);

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
			deleteItem(pos);
			pos = -1;
			break;
		case 2:
//			Intent intent = new Intent(mContext, MapActivity.class);
//			Log.e("arItem.get(pos).isbn", ""+arItem.get(pos).getDealLocation());
//			intent.putExtra("location", arItem.get(pos).getDealLocation());
//			startActivity(intent);
		}
		return true;
	}

	private void deleteItem(int position) {
//		if (dataManager.deleteSms(arItem.get(pos).getIsbn())) {
//			arItem.remove(position);
//			lv.clearChoices();
//			mAdapter.notifyDataSetChanged();
//			utill.tst(mContext, "�����Ϸ�");
//		} else {
//			utill.tst(mContext, "��������");
//		}
	}
}