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
import com.tleaf.lifelog.listAdapter.AllListAdapter;
import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.model.Call;
import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.model.Location;
import com.tleaf.lifelog.model.Photo;
import com.tleaf.lifelog.model.Sms;
import com.tleaf.lifelog.network.DbConnector;
import com.tleaf.lifelog.network.OnDataListener;
import com.tleaf.lifelog.pkg.FragmentListener;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.Util;
public class AllFragment  extends Fragment implements OnDataListener  { 
	private static final String TAG = "AllFragment";

	private Context mContext;
	private ListView lv;
	private AllListAdapter mAdapter = null;

	private int pos = -1;
	private FragmentListener fListener;
	
	private List<Lifelog> list;

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

		list = new ArrayList<Lifelog>();

		String DBNAME = "jin";
		DbConnector db = new DbConnector(this, mContext.getApplicationContext());
		db.getData(DBNAME, "lifelogs");

		Mylog.i("list.size()", ""+list.size());
		//		if(list.size() <= 0)
		//			return rootView;

		mAdapter = new AllListAdapter(mContext, R.layout.item_all, list);
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
			break;
		}
		return true;
	}

	@Override
	public void onSendData(String data) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Mylog.i(TAG, data);
		try {
			JSONObject jsonObject = new JSONObject(data);
			Mylog.i(TAG, "version : " + jsonObject.getString("version"));
			Mylog.i(TAG, "count : " + jsonObject.getString("count"));
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			Lifelog log = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				String type = jsonArray.getJSONObject(i).getString("logType");
				Mylog.i("type", type);

				switch(type) {
				case Util.BOOKMARK:
					log = gson.fromJson(jsonArray.getString(i), Bookmark.class);
					break;
				case Util.CALL:
					log = gson.fromJson(jsonArray.getString(i), Call.class);
					break;
				case Util.PHOTO:
					log = gson.fromJson(jsonArray.getString(i), Photo.class);
					break;
				case Util.POSITON:
					log = gson.fromJson(jsonArray.getString(i), Location.class);
					break;
				case Util.SMS:
					log = gson.fromJson(jsonArray.getString(i), Sms.class);
					break;
				}
				list.add(log);
			}
			Mylog.i("onSendData list.size()", ""+list.size());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally{
			mAdapter.notifyDataSetChanged();
		}
	}

}