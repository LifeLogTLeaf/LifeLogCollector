package com.tleaf.lifelog.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.pkg.FragmentListener;
import com.tleaf.lifelog.pkg.PagerAdapter;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.Preference;
import com.tleaf.lifelog.util.Util;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener, FragmentListener {

	public static PagerAdapter mPagerAdapter;
	public static ViewPager mViewPager;
	private Preference pref;
//	private boolean installation;
	private Context context;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		pref = new Preference(context);
		setInstallTime();

		initViewPager();
	}

	private void initViewPager(){
	
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		final ActionBar actionBar = getActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mPagerAdapter.getCount(); i++) {
			actionBar.addTab(
					actionBar.newTab()
					.setText(mPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	public void onPause() {
		super.onPause();
		setInstallTime();
	}


	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}


	private void setInstallTime() {
		if(!pref.getBooleanPref("installation")) { 
			long currentTime = Util.getCurrentTime();
			Mylog.i("installTime", Util.formatLongTime(currentTime));
			pref.setLongPref("installTime", Util.getCurrentTime());
			pref.setBooleanPref("installation", true);
			Mylog.i("installation true", ""+pref.getBooleanPref("installation"));
		}
	}

	/* 2014.08.18 By Young 페이스북 연동에 필요한 해쉬키를 로드한다. */
	private void loadHashKey() {
		// Add code to print out the key hash
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
				System.out.println("hello");
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	/* 2014.08.20 By Young 백그라운드 서비스인 데이터 업로드 서비스를 실핸한다. */
	private void startUploaderService() {
		Intent intent = new Intent("com.tleaf.service.uploader");
		startService(intent);
	}

}
