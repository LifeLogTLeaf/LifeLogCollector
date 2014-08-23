package com.tleaf.lifelog.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tleaf.lifelog.R;
import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.model.Lifelog;

public class MapViewActivity extends Activity {
	
	private Marker marker;
	private GoogleMap map;
	private Map<String,Lifelog> markerInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		Log.i("OnCreate", "MapViewActivity");
		
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		Bookmark bookmark = new Bookmark();
		bookmark.setId("susu-20140819132954-bookmark");
		bookmark.setSiteUrl("cloudant.com/doc");
		bookmark.setTitle("Cloudant Document");
		
		Log.i("Create", "Bookmark");
		
		ArrayList<Lifelog> queryData = new ArrayList<Lifelog>();
		queryData.add( bookmark ); // 가져오는 가정을 위해 객체 추가
		
		markerInfo = new HashMap<String, Lifelog>();
		
		for( Lifelog i:queryData )
		{
			marker = map.addMarker( new MarkerOptions().position(new LatLng(37.485784, 126.924912)));
			markerInfo.put( marker.getId(), i);
		}
		
		Log.i("Create", "markerInfoData");
		
		map.setOnMarkerClickListener( new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				Bookmark markerDocu = (Bookmark)markerInfo.get( arg0.getId() );
				Toast.makeText( MapViewActivity.this, markerDocu.toString(), Toast.LENGTH_LONG).show();				
				return false;
			}
			
		} );
		
		Log.d("Set", "OnMarkerClickListener");
		
	}

}