package com.tleaf.lifelog.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.tleaf.lifelog.network.CouchDBLiteTask;
import com.tleaf.lifelog.util.Mylog;

public class UploaderService extends Service{
	private static final String TAG = "UPLOADER SERVICE";
	private CouchDBLiteTask connector;
	
	
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
	
	
}
