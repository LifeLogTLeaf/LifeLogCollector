package com.tleaf.lifelog.fragment;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
import com.tleaf.lifelog.model.FacebookUserInfo;
import com.tleaf.lifelog.model.UserInfo;
import com.tleaf.lifelog.util.Mylog;

public class LoginFragment extends Fragment implements StatusCallback {
	private static final String TAG = "LOGIN FRAGMENT";
	private FragmentManager mFragmentManager;
	private UiLifecycleHelper uiHelper; // to track the session and trigger a
										// session state change listener.
	private ArrayList<String> mPermissionList;

	public LoginFragment(FragmentManager fm) {
		this.mFragmentManager = fm;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Mylog.i(TAG, "Login Fragment is created");
		uiHelper = new UiLifecycleHelper(getActivity(), this);
		uiHelper.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		RelativeLayout rootView = (RelativeLayout) inflater.inflate(
				R.layout.fragment_login, container, false);
		LoginButton facebook_login_btn = (LoginButton) rootView
				.findViewById(R.id.facebook_login_btn);
		facebook_login_btn.setFragment(this);

		mPermissionList = new ArrayList<String>();
		mPermissionList.add("read_stream");
		facebook_login_btn.setReadPermissions(mPermissionList);
		// facebook_login_btn.setReadPermissions(Arrays.asList("user_likes",
		// "user_status", "read_stream"));

		return rootView;
	}

	@Override
	public void onResume() {
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
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Mylog.i(TAG, "Login fragment is attached");
	}

	@Override
	public void call(Session session, SessionState state, Exception exception) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "session call start");
		onSessionStateChange(session, state, exception);
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Mylog.i(TAG, "Logged in... ");
			sendUserDataToServer(session);
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
		// 그래프 API를 사용해서 호출할때 쓰는 소스코드
		if (session == null)
			return;

		// 새로운 도큐먼트 생성이 아니라 업데이트로 가기 때문에
		// 여기서 먼저 디비에서 .UserInfor라는 데이터를 가져와서 rev 값을 읽어온다
		// 그리고 이 rev값을 이용해서 업데이트를 진행한다. 추후
		final UserInfo UserInfo = new UserInfo();

		RequestAsyncTask reqeust2 = new Request(session, "/me", null,
				HttpMethod.GET, new Request.Callback() {
					public void onCompleted(Response response) {
						/* handle the result */
						Mylog.i(TAG, response.getRawResponse());
						FacebookUserInfo FacebookUserInfo = new FacebookUserInfo();
						// access Token 저장
						FacebookUserInfo.setFacebookAccesskey(session
								.getAccessToken());
						// facebook id 저장
						FacebookUserInfo.setFacebookId(session
								.getApplicationId());
						// facebook permission 저장
						// UserInfor.setFacebookPermission(session.getPermissions());
						UserInfo.setUserFacebookUserInfo(FacebookUserInfo);

						try {
							JSONObject json = new JSONObject(response
									.getRawResponse());
							UserInfo.setGender(json.getString("gender"));
							UserInfo.setUserName(json.getString("name"));
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// UI쓰레드에서 메인프래그먼트를 실행하게 해준다.
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Mylog.i(TAG, "replace to MAIN FRAGMENT...");
								// FragmentTransaction ft =
								// mFragmentManager.beginTransaction();
								// MainFragment mainFragment = new
								// MainFragment(mFragmentManager);
								// ft.replace(R.id.fragment_container,
								// mainFragment);
								// ft.commit();
								FragmentTransaction ft = mFragmentManager
										.beginTransaction();
								MainFragment mainFragment = new MainFragment(
										mFragmentManager);
								ft.replace(R.id.fragment_container,
										mainFragment);
								ft.commit();
							}
						});
					}
				}).executeAsync();

	}

}