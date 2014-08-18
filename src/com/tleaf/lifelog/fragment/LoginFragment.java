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
import com.tleaf.lifelog.model.FacebookUserInfor;
import com.tleaf.lifelog.model.UserInfor;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.network.CouchDBConnector;

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
	 * 2014.08.14 Young �ъ슜�먭� �섏씠�ㅻ턿 濡쒓렇�몄쓣 �꾨즺�섎㈃ 洹��몄뀡��媛��怨��ъ슜�먯쓽 �뺣낫瑜��쎌뼱�⑤떎. �쎌뼱���뺣낫���곕━
	 * �쒕쾭����옣�댁꽌 愿�━�쒕떎. ( 踰뺣쪧���댁뒋 �⑥븘�덉쓬 - �섏씠�ㅻ턿 �쒓났 �곗씠�곗쓽 媛�났泥�)
	 */
	private void sendUserDataToServer(final Session session) {
		// 洹몃옒��API瑜��ъ슜�댁꽌 �몄텧�좊븣 �곕뒗 �뚯뒪肄붾뱶
		if(session == null ) return;
		
		//�덈줈���꾪걧癒쇳듃 �앹꽦���꾨땲���낅뜲�댄듃濡�媛�린 �뚮Ц��
		//�ш린��癒쇱� �붾퉬�먯꽌 .userInfor�쇰뒗 �곗씠�곕� 媛�졇��꽌 rev 媛믪쓣 �쎌뼱�⑤떎
		//洹몃━怨���rev媛믪쓣 �댁슜�댁꽌 �낅뜲�댄듃瑜�吏꾪뻾�쒕떎. 異뷀썑
		final UserInfor userInfor = new UserInfor();
		
		RequestAsyncTask reqeust2 = ( new Request(
				session, 
				"/me", 
				null,
				HttpMethod.GET, new Request.Callback() {
					public void onCompleted(Response response) {
						/* handle the result */
						Mylog.i(TAG, response.getRawResponse());
						FacebookUserInfor facebookUserInfor = new FacebookUserInfor();
						// access Token ��옣 
						facebookUserInfor.setFacebookAccesskey(session.getAccessToken());
						// facebook id ��옣 
						facebookUserInfor.setFacebookId(session.getApplicationId());
						// facebook permission ��옣
						//userInfor.setFacebookPermission(session.getPermissions());
						userInfor.setUserFacebookUserInfor(facebookUserInfor);
						
						try {
							JSONObject json = new JSONObject(response.getRawResponse());
							userInfor.setGender(json.getString("gender"));
							userInfor.setUserName(json.getString("name"));
							CouchDBConnector couchTask = new CouchDBConnector("young", "post", "userinsert");
							couchTask.execute(userInfor);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//UI�곕젅�쒖뿉��硫붿씤�꾨옒洹몃㉫�몃� �ㅽ뻾�섍쾶 �댁���
						getActivity().runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() {
	                    		Mylog.i(TAG, "replace to MAIN FRAGMENT...");
	                    		FragmentTransaction ft = mFragmentManager.beginTransaction();
	                    		MainFragment mainFragment = new MainFragment(mFragmentManager);
	                    		ft.replace(R.id.fragment_container, mainFragment);
	                    		ft.commit();
	                        }
	                    });
					}
				})).executeAsync();
		
	}
}
