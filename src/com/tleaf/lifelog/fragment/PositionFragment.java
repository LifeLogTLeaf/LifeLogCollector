package com.tleaf.lifelog.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
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
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tleaf.lifelog.R;
import com.tleaf.lifelog.map.LatLngInterpolator;
import com.tleaf.lifelog.map.MarkerAnimation;
import com.tleaf.lifelog.pkg.FragmentListener;

public class PositionFragment extends Fragment {

	private Context mContext;
	private ListView lv;
	private int pos = -1;
	private MapView mapView;
	private GoogleMap map;
	ArrayList<MarkerOptions> markers;
	float previousZoomLevel;
	MarkerOptions animationMarker;
	Marker marker;
	LatLngInterpolator lli;

	private boolean isZooming = false;

	private FragmentListener fListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
		fListener = (FragmentListener) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_mapview, container, false);
		Log.e("first onCreateView", "");

		/*
		 * 2014.8.20 WED Edited By Susu Included MapView FragmentLayout and
		 * Disabled Original Commands
		 */

		// Gets the MapView from the XML layout and creates it
		mapView = (MapView) v.findViewById(R.id.mapview);
		mapView.onCreate(savedInstanceState);

		// Gets to GoogleMap from the MapView and Initializes stuffs
		map = mapView.getMap();
		map.getUiSettings().setMyLocationButtonEnabled(false);
		map.setMyLocationEnabled(false);

		MapsInitializer.initialize(this.getActivity());

		// Updates the location and zoom of the MapView
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
				new LatLng(37.503711, 127.045137), 10.0f);
		previousZoomLevel = 14.0f;
		map.moveCamera(cameraUpdate);

		// Creates Dummy Data
		/**
		 * Alpha of the Marker = The Length of an Marker Color of the Marker
		 * The Time of the Event Size of the Marker = Error If Google Map's Zoom
		 * Reaches some Point, Markers that are too small or too big should be
		 * Invisible
		 **/

		markers = new ArrayList<MarkerOptions>();

		markers.add(createMarker(37.485764f, 126.924935f, 0.0f, 9.0f, 5 * 5));
		markers.add(createMarker(37.484278f, 126.926902f, 9.0f, 9.10f, 500));
		markers.add(createMarker(37.481621f, 126.941922f, 9.10f, 9.15f, 500));
		markers.add(createMarker(37.483341f, 126.951900f, 9.15f, 9.20f, 500));
		markers.add(createMarker(37.477401f, 126.985030f, 9.25f, 9.30f, 500));
		markers.add(createMarker(37.486391f, 127.005887f, 9.30f, 9.35f, 500));
		markers.add(createMarker(37.495482f, 127.014985f, 9.35f, 9.40f, 500));
		markers.add(createMarker(37.499874f, 127.038889f, 9.40f, 9.45f, 500));
		markers.add(createMarker(37.486391f, 127.005887f, 9.30f, 9.35f, 500));
		markers.add(createMarker(37.503653f, 127.044983f, 9.50f, 18.0f, 20 * 5));
		markers.add(createMarker(37.475892f, 126.978452f, 19.0f, 20.0f, 100));
		markers.add(createMarker(37.503653f, 127.044983f, 21.0f, 23.0f, 20 * 5));

		for (MarkerOptions i : markers) {
			map.addMarker(i);
		}

		map.setOnCameraChangeListener(getCameraChangeListener());

		animationMarker = new MarkerOptions();
		animationMarker.position(new LatLng(37.485000f, 126.924000f));
		marker = map.addMarker(animationMarker);

		lli = new LatLngInterpolator.Spherical();

		return v;
	}

	public OnCameraChangeListener getCameraChangeListener() {
		return new OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition position) {
				Log.i("Zoom", "Zoom: " + position.zoom);

				if (previousZoomLevel != position.zoom) {
					isZooming = true;

					previousZoomLevel = position.zoom;

					map.clear();
					markers.clear();

					markers.add(createMarker(37.485764f, 126.924935f, 0.0f,
							9.0f, 5 * 5));
					markers.add(createMarker(37.484278f, 126.926902f, 9.0f,
							9.10f, 500));
					markers.add(createMarker(37.481621f, 126.941922f, 9.10f,
							9.15f, 500));
					markers.add(createMarker(37.483341f, 126.951900f, 9.15f,
							9.20f, 500));
					markers.add(createMarker(37.477401f, 126.985030f, 9.25f,
							9.30f, 500));
					markers.add(createMarker(37.486391f, 127.005887f, 9.30f,
							9.35f, 500));
					markers.add(createMarker(37.495482f, 127.014985f, 9.35f,
							9.40f, 500));
					markers.add(createMarker(37.499874f, 127.038889f, 9.40f,
							9.45f, 500));
					markers.add(createMarker(37.486391f, 127.005887f, 9.30f,
							9.35f, 500));
					markers.add(createMarker(37.503653f, 127.044983f, 9.50f,
							18.0f, 50));
					markers.add(createMarker(37.475892f, 126.978452f, 19.0f,
							22.0f, 100));
					markers.add(createMarker(37.503653f, 127.044983f, 22.0f,
							24.0f, 50));

					for (MarkerOptions i : markers) {
						map.addMarker(i);
					}

					MarkerAnimation.animateMarkerToICS(marker, new LatLng(
							37.484278f, 126.926902f), lli);
					MarkerAnimation.animateMarkerToICS(marker, new LatLng(
							37.481621f, 126.941922f), lli);
					MarkerAnimation.animateMarkerToICS(marker, new LatLng(
							37.483341f, 126.951900f), lli);
					MarkerAnimation.animateMarkerToICS(marker, new LatLng(
							37.477401f, 126.985030f), lli);
					MarkerAnimation.animateMarkerToICS(marker, new LatLng(
							37.486391f, 127.005887f), lli);
					MarkerAnimation.animateMarkerToICS(marker, new LatLng(
							37.503653f, 127.044983f), lli);
				}
				previousZoomLevel = position.zoom;
			}
		};
	}

	public MarkerOptions createMarker(float lat, float lng, float startTime,
			float endTime, float error) {
		MarkerOptions marker = new MarkerOptions();

		int size = (int) (error * previousZoomLevel / 20);
		int time = (int) ((startTime + endTime) / 2);

		// Setting The Size of the Bitmap
		Bitmap bitmap = Bitmap
				.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		// Fill the Bitmap image with Translucent Color
		bitmap.eraseColor(0x00000000);

		// Draw on the Bitmap With Color Determined by the Time
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setColor((int) ((0xff0000ff * (24 - time) + 0xffff0000 * time) / 24));

		if (size < 350)
			canvas.drawCircle(size * 0.5f, size * 0.5f, size * 0.5f, paint);

		// Setting Aplha by the length of the Time
		if ((endTime - startTime) < 1.0f)
			marker.alpha(0.15f);
		else
			marker.alpha((endTime - startTime) / previousZoomLevel);

		// Setting Position of the Marker
		marker.position(new LatLng(lat, lng));
		marker.flat(true);

		marker.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
		marker.anchor(0.5f, 0.5f);

		return marker;
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

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return true;
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