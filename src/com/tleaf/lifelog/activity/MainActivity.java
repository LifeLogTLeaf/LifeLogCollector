package com.tleaf.lifelog.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;

import com.tleaf.lifelog.R;
import com.tleaf.lifelog.fragment.LoginFragment;
import com.tleaf.lifelog.service.DataAccessService;
import com.tleaf.lifelog.util.MessageHandler;
import com.tleaf.lifelog.util.Mylog;

public class MainActivity extends FragmentActivity implements ServiceConnection {
	private static final String TAG = "MAIN ACTIVITY";
	private LoginFragment mLoginFragment;
	private FragmentManager mFragmentManager;
	private Messenger serviceMessenger; //2014.08.19 서비스컴포넌트에 데이터를 보낼때사용합니다. 프래그먼트 전체가 공유합니다.
	private boolean isBind; // //2014.08.19 서비스컴포넌트와의 바인딩 상태입니다.
	
	/**
	 * 2014.08.18 MainActivity Life Cycle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Mylog.i(TAG, "Created MainActivity");
		setContentView(R.layout.activity_main);
		
		// 2014.08.19 by Young
		// 백그라운드에 서비스가 제대로 동작하는지 확인하고 서비스가 죽었거나
		// 오류가 발생했다면 서비스를 다시 생성해준다. 지금은 생
		// startDataAccessService();
		
		// 2014.08.19 by Young 메인액티비티를 초기화시켜주는 메서드입니다.
		init();
		
		// 2014.08.19 by Young
		// 멘인액티비티에서 모든일을 다 끝내고 최초 시작할 프래그먼트를 선택한다.
		// 이때 로그인이 되있으면 메인프래그먼트를 안되있으면 로그인프래그먼트를 부른다.
		// SharedPreference에 저장하고 관리할 예정
		
		// 2014.08.19 by Young
		// 추가 서비스 컴포넌트와 연결이 되면 프래그먼트를 부른다 .
		// 왜냐하면 프래그먼트에 Messenger객체를 넘겨줘야 하기 때문입니다.
		// 따라서 프래그먼트 시작 부분은 아래 콜백으로 넘긴다. 지금은 생략
		// loadHashKey();


		 Intent intent = new Intent(this, BookmarkShareActivity.class);
		 startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		// 2014.08.18 by Young 메인액티비티가 죽으면 바인딩이 해제됩니다.
		if (isBind) {
            Mylog.i(TAG, "서비스와 바인딩을 해제한다.");
            unbindService(this);
            isBind = false;
        }
		
		// 2014.08.18 by Young 서비스를 중지하는 코드 ( 테스트 코드입니다 )
		Intent intent = new Intent("com.tleaf.service.uploader");
		stopService(intent);
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
	 * 2014.08.18 By Young 메인액티비티 초기화 메서드입니다.
	 */
	private void init(){
		// 2014.08.18 By Young 서비스를 바인딩합니다.
		// 지금은 서비스 바인딩 생략.
//        Intent intent = new Intent(DataAccessService.INTENT_ACTION);
//        bindService(intent, this, Context.BIND_AUTO_CREATE);
	}
	
	/**
	 * 2014.08.18 By Young 로그인 프래그먼트를 실행한다.
	 */
	private void startLoginFragment() {
		mFragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		LoginFragment loginFragment = new LoginFragment(mFragmentManager, serviceMessenger);
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
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	/**
	 * 2014.08.19 By Young 백그라운드 서비스인 데이터 접근 서비스를 실핸한다.
	 */
	private void startDataAccessService() {
		Intent intent = new Intent("com.tleaf.service.uploader");
		startService(intent);
	}

	/**
	 * 2014.08.19 By Young 서비스컴포넌트와의 연결상태를 콜백받는 메서드입니다.
	 */
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "데이터접근서비스와 메인액티비티가 연결되었습니다.");
		// 2014.08.19 by Young
		// 여기서 인자로 받는 IBinder service를 사용해서 서비스컴포넌트와 커뮤니케이션합니다.
		serviceMessenger = new Messenger(service);
		isBind = true;
		startLoginFragment();
		
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "데이터접근서비스와 메인액티비티가 연결이 해제되었습니다.");
	}
}
