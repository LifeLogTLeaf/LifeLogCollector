package com.tleaf.lifelog.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
import com.tleaf.lifelog.util.Mylog;

public class MainActivity extends FragmentActivity {
	private static final String TAG = "MAIN ACTIVITY";
	private LoginFragment mLoginFragment;
	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Mylog.i(TAG, "Created MainActivity!!");
		setContentView(R.layout.activity_main);
		init();
		loadHashKey();
		// 멘인액티비티에서 모든일을 다 끝내고 최초 시작할 프래그먼트를 선택한다.
		// 이때 로그인이 되있으면 메인프래그먼트를 안되있으면 로그인프래그먼트를 부른다.
		// SharedPreference에 저장하고 관리할 예정
		// ---지금은 생략---
		/*
		 * if(true){
		 * 
		 * } else {
		 * 
		 * }
		 */
	}

	private void init() {
		mFragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		LoginFragment loginFragment = new LoginFragment(mFragmentManager);
		ft.add(R.id.fragment_container, loginFragment);
		ft.commit();
	}

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
}
