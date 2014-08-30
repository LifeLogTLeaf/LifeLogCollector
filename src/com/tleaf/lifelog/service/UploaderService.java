package com.tleaf.lifelog.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;

import com.tleaf.lifelog.util.Mylog;

/**
 * 2014.08.29
 * 
 * @author susu Starting to Implement Location Services
 */
public class UploaderService extends Service implements LocationListener {
	private static final String TAG = "UPLOADER SERVICE";
	//private CouchDBLiteTask connector;
	// Location Lisntener --

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	// -- Location Listener

	// Service --

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "Uploader Service is starting");

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Mylog.i(TAG, "Uploader Service is dead");

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	// -- Service

}
