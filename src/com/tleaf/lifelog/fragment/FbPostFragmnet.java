package com.tleaf.lifelog.fragment;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.tleaf.lifelog.R;
import com.tleaf.lifelog.dbaccess.DAO;
import com.tleaf.lifelog.dbaccess.DBListener;
import com.tleaf.lifelog.model.FacebookPost;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.Preference;

public class FbPostFragmnet extends Fragment implements DBListener,
		StatusCallback {
	private static final String TAG = "북마크 프래그먼트";
	private Context mContext;
	private String dbName;
	private EditText post_text;
	private FbPostFragmnet fragment;
	private Preference pre;
	private UiLifecycleHelper uiHelper; // to track the session and trigger a

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		pre = new Preference(getActivity().getApplicationContext());
		dbName = pre.getStringPref("userId");
		uiHelper = new UiLifecycleHelper(getActivity(), this);
		uiHelper.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_fbpost, container,
				false);
		fragment = this;

		rootView.findViewById(R.id.replication_start_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						replication();
					}
				});

		rootView.findViewById(R.id.createdoc_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						postData();
					}
				});

		post_text = (EditText) rootView.findViewById(R.id.post_text);

		return rootView;
	}

	// 페이스북으로 데이터 전송하는것은 유저가 퍼미션을 없앴을수도 있기때문에 항상 확인해야 한다.
	public void postData() {
		Mylog.i(TAG, "posting data");
		Session session = Session.getActiveSession();
		session.addCallback(this);

		boolean isPublish = false;
		final String message = post_text.getText().toString();
		

		for (String permission : session.getPermissions()) {
			Mylog.i(TAG, "permission : " + permission);
			if (permission.equals("publish_actions")) {
				isPublish = true;
			} else {
				isPublish = false;
			}
		}

		if (!isPublish) {
			Mylog.i(TAG, "permission add ");
			Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
					this, Arrays.asList("publish_actions"));
			session.requestNewPublishPermissions(newPermissionsRequest);
		}

		if (session != null && (session.isOpened() || session.isClosed())) {
			if (session.isOpened()) {
				Mylog.i(TAG, "sessiong is closed");
			} else {
				Mylog.i(TAG, "sessiong is opend");
			}
		}

		Bundle params = new Bundle();
		params.putString("message", message);
		/* make the API call */
		new Request(session, "/me/feed", params, HttpMethod.POST,
				new Request.Callback() {
					public void onCompleted(Response response) {
						/* handle the result */
						Mylog.i(TAG, response.toString());
						DAO db = new DAO(fragment, getActivity()
								.getApplicationContext());
						FacebookPost fbpost = new FacebookPost();
						fbpost.setContent(message);
						fbpost.setlogType("facebookPost");
						db.postData(dbName, fbpost, "touch");
					}
				}).executeAsync();

	}

	void replication() {
		DAO db = new DAO(this, getActivity().getApplicationContext());
		db.startReplication(dbName);
	}

	@Override
	public void onSendData(String data) {
		Mylog.i(TAG, "status : " + data);
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss(); // 닫기
			}
		});
		alert.setMessage("포스팅이 성공적으로 완료되었습니다.");
		alert.show();
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
		mContext = activity;
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
		} else if (state.isClosed()) {
			Mylog.i(TAG, "Logged out...");
		}
	}

}
