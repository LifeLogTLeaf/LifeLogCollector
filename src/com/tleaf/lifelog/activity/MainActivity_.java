package com.tleaf.lifelog.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.fragment.LoginFragment;
import com.tleaf.lifelog.service.UploaderService;
import com.tleaf.lifelog.util.Mylog;

public class MainActivity_ extends FragmentActivity {
	private LoginFragment mLoginFragment;
	private FragmentManager mFragmentManager;

	/**
	 * 2014.08.18 MainActivity Life Cycle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Mylog.i(TAG, "Created MainActivity");
		setContentView(R.layout.activity_main);

//		init();
//		loadHashKey();

		// 멘인액티비티에서 모든일을 다 끝내고 최초 시작할 프래그먼트를 선택한다.
		// 이때 로그인이 되있으면 메인프래그먼트를 안되있으면 로그인프래그먼트를 부른다.
		// SharedPreference에 저장하고 관리할 예정
		// --- 지금은 생략 ---
		// init();
		// loadHashKey();

		// 백그라운드에 서비스가 제대로 동작하는지 확인하고 서비스가 죽었거나
		// 오류가 발생했다면 서비스를 다시 생성해준다.
		// --- 지금은 생략 ---
		//startUploaderService();
		
		Intent intent = new Intent(this, BookmarkShareActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//2014.08.18 by Young
		//서비스를 중지하는 코드 ( 테스트 코드입니다 )
		//Intent intent = new Intent("com.tleaf.service.uploader");
		//stopService(intent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/**
	 * 2014.08.18 By Young 로그인 프래그먼트를 실행한다.
	 */
	private void init() {
		mFragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		LoginFragment loginFragment = new LoginFragment(mFragmentManager);
		ft.add(R.id.fragment_container, loginFragment);
		ft.commit();
	}

	/**
	 * 2014.08.18 By Young 페이스북 연동에 필요한 해쉬키를 로드한다.
	 */
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

	/**
	 * By Young 백그라운드 서비스인 데이터 업로드 서비스를 실핸한다.
	 */
	private void startUploaderService() {
		Intent intent = new Intent("com.tleaf.service.uploader");
		startService(intent);
	}
}
