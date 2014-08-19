package com.tleaf.lifelog.fragment;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.tleaf.lifelog.model.Bookmark;
import com.tleaf.lifelog.model.FacebookUserInfor;
import com.tleaf.lifelog.model.UserInfor;
import com.tleaf.lifelog.service.DataAccessService;
import com.tleaf.lifelog.util.MessageHandler;
import com.tleaf.lifelog.util.MessageListener;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.database.CouchDBTask;
/**
 * 2014.08.18 
 * @author jangyoungjin
 * 페이스북 로그인관련 프래그먼트입니다.
 */
public class LoginFragment extends Fragment implements StatusCallback, MessageListener {
	private static final String TAG = "로그인프래그먼트";
	private FragmentManager mFragmentManager;
	private UiLifecycleHelper uiHelper; // to track the session and trigger a session state change listener.
	private ArrayList<String> mPermissionList; // 사용자에게 보여질 페이스북 엑세스 권한 
	
	//서비스와 메시지를 주고 받기 위해서 필요한 멤버 변수 입니다.
	private Messenger serviceMessenger;
	private Messenger loginFragmentMessenger;
	private MessageHandler mHandler;
	
	
	public LoginFragment(FragmentManager fm, Messenger messenger) {
		this.mFragmentManager = fm;
		this.serviceMessenger = messenger;
	}
	
	/**
	 * 기본적인 프래그먼트 라이프사이클 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Mylog.i(TAG, "로그인프래그먼트가 생성되었습니다.");
		uiHelper = new UiLifecycleHelper(getActivity(), this);
		uiHelper.onCreate(savedInstanceState);
		
		//받은 메시지를 처리할 핸들러
		mHandler = new MessageHandler("componet", this);
		loginFragmentMessenger = new Messenger(mHandler);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_login, container, false);
		LoginButton facebook_login_btn = (LoginButton) rootView.findViewById(R.id.facebook_login_btn);
		facebook_login_btn.setFragment(this);

		mPermissionList = new ArrayList<String>();
		mPermissionList.add("read_stream");
		facebook_login_btn.setReadPermissions(mPermissionList);
		
		// facebook_login_btn.setReadPermissions(Arrays.asList("user_likes",
		// "user_status", "read_stream"));
		
		//테스트코드
		rootView.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bookmark bookmark = new Bookmark();
				bookmark.setTitle("짜장면");
				bookmark.setType("bookmark");
				bookmark.setSiteUrl("www.korea.com");
				bookmark.setId("young20141011");
				sendObjMessageToService(MessageHandler.MSG_SAVE_DOCUMENT, bookmark);
			}
		});;
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
		Mylog.i(TAG, "로그인프래그먼트가 메인액티비티에 찰싹 달라붙었습니다.");
	}
	
	/**
	 * 
	 */
	/* 페이스북 세션 콜 메서드 */
	@Override
	public void call(Session session, SessionState state, Exception exception) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "session call start");
		onSessionStateChange(session, state, exception);
	}

	/* 페이스북 세션 체크 메서드*/
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
		if(session == null ) return;
		
		//새로운 도큐먼트 생성이 아니라 업데이트로 가기 때문에
		//여기서 먼저 디비에서 .userInfor라는 데이터를 가져와서 rev 값을 읽어온다
		//그리고 이 rev값을 이용해서 업데이트를 진행한다. 추후
		final UserInfor userInfor = new UserInfor();
		
		RequestAsyncTask reqeust2 = new Request(
				session, 
				"/me", 
				null,
				HttpMethod.GET, new Request.Callback() {
					public void onCompleted(Response response) {
						/* handle the result */
						Mylog.i(TAG, response.getRawResponse());
						FacebookUserInfor facebookUserInfor = new FacebookUserInfor();
						// access Token 저장 
						facebookUserInfor.setFacebookAccesskey(session.getAccessToken());
						// facebook id 저장 
						facebookUserInfor.setFacebookId(session.getApplicationId());
						// facebook permission 저장
						//userInfor.setFacebookPermission(session.getPermissions());
						userInfor.setUserFacebookUserInfor(facebookUserInfor);
						
						try {
							JSONObject json = new JSONObject(response.getRawResponse());
							userInfor.setGender(json.getString("gender"));
							userInfor.setUserName(json.getString("name"));
							sendObjMessageToService(MessageHandler.MSG_SAVE_DOCUMENT, userInfor);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
	                    Mylog.i(TAG, "페이스북 로그인 완료 --> 메인프래그먼트 실행 ");
	                    FragmentTransaction ft = mFragmentManager.beginTransaction();
	                    MainFragment mainFragment = new MainFragment(mFragmentManager);
	                    ft.replace(R.id.fragment_container, mainFragment);
	                    ft.commit();
	                    
					}
				}).executeAsync();
		
	}
	
	
    /* 
     * 2014.08.19 by Young  
     * 서비스컴포넌트에 객체 메시지를 전달할때 사용하는 메서드 입니다.
     */
    private void sendObjMessageToService(int megType, Object obj) {
        Message msg = Message.obtain(null, megType, obj);
        msg.replyTo = loginFragmentMessenger; //메시지에 메시지 발신자 정보를 넣어준다.
        Mylog.i(TAG, megType + " 을/를 서비스에 보낸다.");
        try {
        	serviceMessenger.send(msg); //메인에서 바인딩된 서비스메신저를 이용해서 서비스 컴포넌트에 메시지를 전달한다.
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    /*
     * 2014.08.19 by Young  
     * 메시지가 들어왔을때 불리는 콜백 함수입니다.
     */
	@Override
	public void onMessage(Object data, Messenger messengerFrom) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "SUCCESS");
		
	}

}
