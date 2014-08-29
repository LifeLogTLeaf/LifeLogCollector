package com.tleaf.lifelog.service;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.tleaf.lifelog.model.Photo;
import com.tleaf.lifelog.network.CouchDBConnector;
import com.tleaf.lifelog.network.CouchDBLiteTask;
import com.tleaf.lifelog.network.WiFiListener;
import com.tleaf.lifelog.network.WiFiListener.OnChangeNetworkStatusListener;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.NotiAlert;
import com.tleaf.lifelog.util.PhotoStorage;

/**
 * 이곳은 서비스 클래스가 돌아간다 서비스클래스의 생명주기는 onCreate -> onStartCommand -> (running) ->
 * stopService -> onDestroy 이다 기존에 onStart도 있었지만, 구글에서 지양하고 있다.
 * 
 * 현재 작동기능 : 사진 업로드
 */
public class UploaderService extends Service implements
		OnChangeNetworkStatusListener {
	private static final String TAG = "UPLOADER SERVICE";
	private static boolean isWiFi = false;
	private UploadThread uploadThread;
	int fileSize = 0;

	private NotificationManager nm = null;

	public static void setWiFi(boolean isWiFi) {
		UploaderService.isWiFi = isWiFi;
	}

	private CouchDBLiteTask connector;
	private WiFiListener wiFiMonitor = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Mylog.i(TAG, "income Service");
		callNoti("서버에 전송합니다");
		initWiFiReceiver();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Mylog.i(TAG, "Uploader Service is starting");
		if (isWiFi) {
			uploadThread = new UploadThread();
			uploadThread.start();
		}

		return super.onStartCommand(intent, flags, startId);
	}

	/** wifi환경을 설정해주는 메소드 */
	void initWiFiReceiver() {
		wiFiMonitor = new WiFiListener(this);
		wiFiMonitor.setOnChangeNetworkStatusListener(changedListener);

		// registerReceiver(wiFiMonitor, new
		// IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		registerReceiver(wiFiMonitor, new IntentFilter(
				WifiManager.WIFI_STATE_CHANGED_ACTION));

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Mylog.i(TAG, "Uploader Service is dead");

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 데이터를 카우db에 보내도록 연결해주는 쓰레드 */
	class UploadThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			ArrayList<Photo> arrFileList;
			PhotoStorage photoStrg = new PhotoStorage(getBaseContext());
			try {

				while (isWiFi) {
					Thread.sleep(1000);
					// 쓰레드를 사용해서 돌려야 하나
					// 카메라 이벤트가 발생할 경우 파일 목록을 읽어온다
					photoStrg = new PhotoStorage(getBaseContext());
					arrFileList = photoStrg.getImagesInfo();

					if (!arrFileList.isEmpty()) {
						CouchDBConnector couchDBTask = new CouchDBConnector(
								"photo", "post", "insert-photo");
						couchDBTask.setContext(getBaseContext());
						couchDBTask.execute(arrFileList);
					}

				}
			} catch (Exception e) {
				e.getStackTrace();
				Mylog.i("photo", "discon WiFi");
				interrupt();
			}

		}
	}

	/** 와이파이 리스너 활성화 부분 */
	public WiFiListener.OnChangeNetworkStatusListener changedListener = new WiFiListener.OnChangeNetworkStatusListener() {
		@Override
		public void OnChanged(int status) {
			switch (status) {
			case WiFiListener.WIFI_STATE_DISABLED:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor] WIFI_STATE_DISABLED", 1000).show();
				Log.i("[WifiMonitor]", "[WifiMonitor] WIFI_STATE_DISABLED");
				setWiFi(false);
				break;

			case WiFiListener.WIFI_STATE_DISABLING:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor] WIFI_STATE_DISABLING", 1000).show();
				Log.i("[WifiMonitor]", "[WifiMonitor] WIFI_STATE_DISABLING");
				setWiFi(false);
				break;

			case WiFiListener.WIFI_STATE_ENABLED:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor] WIFI_STATE_ENABLED", 1000).show();
				Log.i("[WifiMonitor]", "[WifiMonitor] WIFI_STATE_ENABLED");
				if (!isWiFi) {
					setWiFi(true);
					uploadThread = new UploadThread();
					uploadThread.start();

				}

				break;

			case WiFiListener.WIFI_STATE_ENABLING:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor] WIFI_STATE_ENABLING", 1000).show();
				Log.i("[WifiMonitor]", "[WifiMonitor] WIFI_STATE_ENABLING");
				setWiFi(true);
				break;

			case WiFiListener.WIFI_STATE_UNKNOWN:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor] WIFI_STATE_UNKNOWN", 1000).show();
				Log.i("[WifiMonitor]", "[WifiMonitor] WIFI_STATE_UNKNOWN");
				setWiFi(true);
				break;

			case WiFiListener.NETWORK_STATE_CONNECTED:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor] NETWORK_STATE_CONNECTED", 1000).show();
				Log.i("[WifiMonitor]", "[WifiMonitor] NETWORK_STATE_CONNECTED");
				setWiFi(true);
				break;

			case WiFiListener.NETWORK_STATE_CONNECTING:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor] NETWORK_STATE_CONNECTING", 1000).show();
				Log.i("[WifiMonitor]", "[WifiMonitor] NETWORK_STATE_CONNECTING");
				setWiFi(true);
				break;

			case WiFiListener.NETWORK_STATE_DISCONNECTED:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor] NETWORK_STATE_DISCONNECTED", 1000)
						.show();
				Log.i("[WifiMonitor]",
						"[WifiMonitor] NETWORK_STATE_DISCONNECTED");
				setWiFi(false);
				break;

			case WiFiListener.NETWORK_STATE_DISCONNECTING:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor] NETWORK_STATE_DISCONNECTING", 1000)
						.show();
				Log.i("[WifiMonitor]",
						"[WifiMonitor] NETWORK_STATE_DISCONNECTING");
				setWiFi(false);
				break;

			case WiFiListener.NETWORK_STATE_SUSPENDED:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor] NETWORK_STATE_SUSPENDED", 1000).show();
				Log.i("[WifiMonitor]", "[WifiMonitor] NETWORK_STATE_SUSPENDED");
				setWiFi(false);
				break;

			case WiFiListener.NETWORK_STATE_UNKNOWN:
				Toast.makeText(getBaseContext(),
						"[WifiMonitor]  WIFI_STATE_DISABLED", 1000).show();
				Log.i("[WifiMonitor]", "[WifiMonitor] WIFI_STATE_DISABLED");
				setWiFi(false);
				break;

			}
		}
	};

	public void callNoti(String message) {
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// PendingIntent를 등록 하고, noti를 클릭시에 어떤 클래스를 호출 할 것인지 등록.
		PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(
				this, NotiAlert.class), 0);

		// status bar 에 등록될 메시지(Tiker, 아이콘, 그리고 noti가 실행될 시간)
		Notification notification = new Notification(
				android.R.drawable.btn_star, message,
				System.currentTimeMillis());

		// List에 표시될 항목
		notification.setLatestEventInfo(this, "파일 업로드", message, intent);

		// noti를 클릭 했을 경우 자동으로 noti Icon 제거
		// notification.flags = notification.flags |
		// notification.FLAG_AUTO_CANCEL;

		// 1234 notification 의 고유아이디
		nm.notify(1234, notification);
		// Toast.makeText(UploaderService.this, "Notification Registered.",
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void OnChanged(int status) {
		// TODO Auto-generated method stub

	}

}
