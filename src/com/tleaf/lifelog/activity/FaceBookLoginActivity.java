package com.tleaf.lifelog.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.tleaf.lifelog.R;
import com.tleaf.lifelog.dbaccess.DAO;
import com.tleaf.lifelog.dbaccess.DBListener;
import com.tleaf.lifelog.model.FacebookUserInfo;
import com.tleaf.lifelog.model.UserInfo;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.Preference;

public class FaceBookLoginActivity extends Activity implements StatusCallback,
		DBListener {
	private static final String TAG = "로그인 액티비티";
	private UiLifecycleHelper uiHelper; // to track the session and trigger a
										// session state change listener.
	private ArrayList<String> mPermissionList;
	private DAO db;
	private Preference pre;
	private String userId = "user2"; // 더미 데이터 입니다.
	private Session session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook);
		loadHashKey();

		db = new DAO(this, getApplicationContext());
		pre = new Preference(getApplicationContext());

		uiHelper = new UiLifecycleHelper(this, this);
		uiHelper.onCreate(savedInstanceState);
		LoginButton facebook_login_btn = (LoginButton) findViewById(R.id.facebook_login_btn);

		mPermissionList = new ArrayList<String>();
		mPermissionList.add("read_stream");

		facebook_login_btn.setReadPermissions(mPermissionList);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void call(Session session, SessionState state, Exception exception) {
		// TODO Auto-generated method stub

	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			if (pre.getBooleanPref("userLogin")) {
				// 세션의 유효성도 검사해야합니다.
				Mylog.i(TAG, "Already Logged in... ");
				Intent intent = new Intent(FaceBookLoginActivity.this,
						MainActivity.class);
				intent.putExtra("session", session);
				startActivity(intent);
				finish();
			} else {
				Mylog.i(TAG, "Logging in... ");
				sendUserDataToServer(session);
			}
		} else if (state.isClosed()) {
			Mylog.i(TAG, "Logged out...");
		} else {
			Mylog.i(TAG, "Logging ...");
		}
	}

	/*
	 * 2014.08.14 Young 사용자가 페이스북 로그인을 완료하면 그 세션을 가지고 사용자의 정보를 읽어온다. 읽어온 정보는 우리
	 * 서버에 저장해서 관리한다. ( 법률적 이슈 남아있음 - 페이스북 제공 데이터의 가공처 )
	 */
	private void sendUserDataToServer(final Session session) {
		this.session = session;
		
		// 그래프 API를 사용해서 호출할때 쓰는 소스코드
		if (session == null)
			return;
		
		RequestAsyncTask reqeust2 = new Request(session, "/me", null,
				HttpMethod.GET, new Request.Callback() {
					public void onCompleted(Response response) {
						/* handle the result */
						Mylog.i(TAG, response.getRawResponse());
						UserInfo userInfo = new UserInfo();
						FacebookUserInfo FacebookUserInfo = new FacebookUserInfo();
						// access Token 저장
						FacebookUserInfo.setFacebookAccesskey(session
								.getAccessToken());
						// facebook id 저장
						FacebookUserInfo.setFacebookId(session
								.getApplicationId());
						// facebook permission 저장
						// UserInfo.setFacebookPermission(session.getPermissions());
						userInfo.setUserFacebookUserInfo(FacebookUserInfo);

						try {
							JSONObject json = new JSONObject(response
									.getRawResponse());
							userInfo.setGender(json.getString("gender"));
							userInfo.setUserName(json.getString("name"));
							userInfo.setUserId(userId);
							pre.setBooleanPref("userLogin", true);
							pre.setStringPref("fbAccesstoken",
									session.getAccessToken());
							pre.setStringPref("userId", userId);

							db.postData(userId, userInfo, "server",
									"facebooklogin");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}).executeAsync();

	}

	@Override
	public void onSendData(String data) {
		// TODO Auto-generated method stub
		// UI쓰레드에서 메인프래그먼트를 실행하게 해준다.
		if (data == null) {
			return;
		}

		JSONObject json = null;
		String result = null;
		try {
			json = new JSONObject(data);
			result = json.getString("result");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Mylog.i(TAG, result);
		if (result.equals("true")) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(FaceBookLoginActivity.this, MainActivity.class);
					intent.putExtra("session", session);
					startActivity(intent);
					finish();
				}
			});
		}
	}

	/* 2014.08.18 By Young 페이스북 연동에 필요한 해쉬키를 로드한다. */
	private void loadHashKey() {
		// Add code to print out the key hash
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Mylog.i("KeyHash:",Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

}
