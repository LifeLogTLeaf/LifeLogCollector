package com.tleaf.lifelog.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.tleaf.lifelog.R;
import com.tleaf.lifelog.db.DataManager;
import com.tleaf.lifelog.listAdapter.SmsListAdapter;
import com.tleaf.lifelog.pkg.FragmentListener;

public class PositionFragment extends Fragment { 

	private Context mContext;
	private ArrayList<Sms> arItem = null;
	private ListView lv;
	private SmsListAdapter mAdapter = null;
	private DataManager dataManager;
	private int pos = -1;
	private MapView mapView;
	private GoogleMap map;

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
		View v = inflater.inflate(R.layout.fragment_mapview, container, false);
		Log.e("first onCreateView", "");
		
		/*
		 * 2014.8.20 WED Edited By Susu
		 * Included MapView FragmentLayout and Disabled Original Commands
		 */

		// Gets the MapView from the XML layout and creates it
		mapView = (MapView) v.findViewById(R.id.mapview);
		mapView.onCreate(savedInstanceState);

		// Gets to GoogleMap from the MapView and does initialization stuff
		map = mapView.getMap();
		map.getUiSettings().setMyLocationButtonEnabled(false);
		map.setMyLocationEnabled(true);

		MapsInitializer.initialize(this.getActivity());

		// Updates the location and zoom of the MapView
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(37.503711, 127.045137), 16);
		map.animateCamera(cameraUpdate);

		return v;
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
		/*switch (item.getItemId()) {
		case 1:
			deleteItem(pos);
			pos = -1;
			break;
		case 2:
//			Intent intent = new Intent(mContext, MapActivity.class);
//			Log.e("arItem.get(pos).isbn", ""+arItem.get(pos).getDealLocation());
//			intent.putExtra("location", arItem.get(pos).getDealLocation());
//			startActivity(intent);
		}*/
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
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		mapView.onResume();
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
 
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}
}