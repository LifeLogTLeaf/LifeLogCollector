package com.tleaf.lifelog.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.tleaf.lifelog.R;
import com.tleaf.lifelog.util.Mylog;

public class MainFragment extends Fragment implements StatusCallback {
	private static final String TAG = "MAIN_Fragment";
	private FragmentManager mFragmentManager;
	private UiLifecycleHelper uiHelper; // to track the session and trigger a
										// session state change listener.
	private TextView user_name;
	private ProfilePictureView user_profile_img;

	public MainFragment(FragmentManager fm) {
		this.mFragmentManager = fm;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Mylog.i(TAG, "Main Fragment is created");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		RelativeLayout rootView = (RelativeLayout) inflater.inflate(
				R.layout.fragment_main, container, false);
		user_name = (TextView) rootView.findViewById(R.id.user_name);
		user_profile_img = (ProfilePictureView) rootView.findViewById(R.id.user_profile_img);
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
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Mylog.i(TAG, "Main fragment is attached");
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
			Mylog.i(TAG, "logged in");
			// - �곕え - 
			reqeustUserProfile(session);
		} else if (state.isClosed()) {
			Mylog.i(TAG, "logged out...");
		}
	}

	private void reqeustUserProfile(final Session session) {
		Request request = Request.newMeRequest(session,
				new GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// TODO Auto-generated method stub
						if (session == Session.getActiveSession() && user != null) {
							//User媛�null媛믪씠 �섎뒗 寃쎌슦媛��덇린�뚮Ц���대� 泥댄겕�댁쨾�쇳븳�� �댁쑀���명꽣��而ㅻ꽖�섎븣臾몄뿉.
							Mylog.i(TAG, " user id : " + user.getId() 
									+ "\n user name : " + user.getName()
									+ "\n user birthday : " + user.getBirthday()
									+ "\n user facebook url : " + user.getLink()
									+ "\n user current location : " + user.getLocation() );
							user_name.setText(user.getName());
							user_profile_img.setProfileId(user.getId());
						} else if(user != null){
							Mylog.i(TAG, "User is NULL so that something is wrong");
						}
					}
				});
		request.executeAsync();
		
		// 洹몃옒��API瑜��ъ슜�댁꽌 �몄텧�좊븣 �곕뒗 �뚯뒪肄붾뱶 
		RequestAsyncTask reqeust2 = new Request(
			    session,
			    "/me",
			    null,
			    HttpMethod.GET,
			    new Request.Callback() {
			        public void onCompleted(Response response) {
			            /* handle the result */
			        	Mylog.i(TAG, response.getRawResponse());
			        }
			    }
			).executeAsync();
		
    	Mylog.i(TAG, "session string : "+session.toString());
    	Mylog.i(TAG, "session access token : "+session.getAccessToken());
    	Mylog.i(TAG, "session application id : "+session.getApplicationId());
    	Mylog.i(TAG, "session permission : "+session.getPermissions());
    	
    	
		
	}

}
