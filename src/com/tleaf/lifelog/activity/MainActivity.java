package com.tleaf.lifelog.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
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

import com.facebook.Session;
import com.tleaf.lifelog.R;
import com.tleaf.lifelog.pkg.FragmentListener;
import com.tleaf.lifelog.pkg.PagerAdapter;
import com.tleaf.lifelog.util.Mylog;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener, FragmentListener {
	static public PagerAdapter mPagerAdapter;
	static public ViewPager mViewPager;
	int saleBookNo;
	String isbn;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 2014.08.20 by young 슬기가 작성한 페이저뷰 설정 부분을
		// 따로 init 메소드로 보내서 설정하게 했습니다.
		init();
		
	}
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



	/* 2014.08.20 by young 메인액티비티에서 필요한 부분을 초기화시켜주는 메소드입니다. */
	private void init(){
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		final ActionBar actionBar = getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	/**
	 *	메인 액티비티에서 발생하는 버튼 터치에 대한 이벤트 리스너 입니다. 
	 */
	public void onClick(View v) {
		/*if (v.getId() == R.id.btn_photo) {
			Intent intent = new Intent(this, PhotoActivity.class);
			startActivity(intent);
		}*/
	}

	/* 2014.08.20 슬기가 주석처리한 부분. */
	// @Override public void onTabUnselected(ActionBar.Tab tab,
	// FragmentTransaction fragmentTransaction) { }
	//
	// @Override public void onTabSelected(ActionBar.Tab tab,
	// FragmentTransaction fragmentTransaction) { // When the given tab is
	// selected, switch to the corresponding page in the ViewPager.
	// mViewPager.setCurrentItem(tab.getPosition()); }
	//
	// @Override public void onTabReselected(ActionBar.Tab tab,
	// FragmentTransaction fragmentTransaction) { }


	/* 2014.08.20 By Young 백그라운드 서비스인 데이터 업로드 서비스를 실핸한다. */
	private void startUploaderService() {
		Intent intent = new Intent("com.tleaf.service.uploader");
		startService(intent);
	}

}
