package com.tleaf.lifelog.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tleaf.lifelog.R;
import com.tleaf.lifelog.dbaccess.DAO;
import com.tleaf.lifelog.dbaccess.DBListener;
import com.tleaf.lifelog.listAdapter.SmsListAdapter;
import com.tleaf.lifelog.log.SmsLog;
import com.tleaf.lifelog.model.Sms;
import com.tleaf.lifelog.pkg.FragmentListener;
import com.tleaf.lifelog.util.Mylog;

public class SmsFragment extends Fragment implements DBListener {
	private final String TAG = "sms fragment";
	
	private Context mContext;
	private ArrayList<Sms> arItem = null;
	private ListView lv;
	private SmsListAdapter mAdapter = null;
	private int pos = -1;
	private FragmentListener fListener;
	private String DBNAME = "jin";

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
		fListener = (FragmentListener) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list, container,
				false);
		Log.e("first onCreateView", "");

		arItem = new ArrayList<Sms>();
		SmsLog sl= new SmsLog(mContext);
		arItem = sl.collectSms();
		saveSmsInDb(arItem);
		
		mAdapter = new SmsListAdapter(mContext, R.layout.item_sms, arItem);
		lv = (ListView) rootView.findViewById(R.id.list);
		lv.setAdapter(mAdapter);
		lv.setOnItemLongClickListener(mItemLongClickListener);

		return rootView;
	}

	private AdapterView.OnItemLongClickListener mItemLongClickListener = new AdapterView.OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
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

	public void saveSmsInDb(ArrayList<Sms> arrItem) {
		DAO db = new DAO(this, mContext);
		for(int i=0; i<arrItem.size(); i++)
			db.postData("jin", arrItem.get(i), "touch");
	}

	@Override
	public void onSendData(String data) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// Mylog.i(TAG, data);
		try {
			JSONObject jsonObject = new JSONObject(data);
			Mylog.i(TAG, "version : " + jsonObject.getString("version"));
			Mylog.i(TAG, "count : " + jsonObject.getString("count"));
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			List<Sms> list = new ArrayList<Sms>();
			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(gson.fromJson(jsonArray.getString(i), Sms.class));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	
}
