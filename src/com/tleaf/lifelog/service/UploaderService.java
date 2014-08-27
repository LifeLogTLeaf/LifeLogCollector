package com.tleaf.lifelog.service;

import java.util.ArrayList;

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
import com.tleaf.lifelog.util.PhotoStorage;
/**이곳은 서비스 클래스가 돌아간다
 * 서비스클래스의 생명주기는 onCreate -> onStartCommand
 * -> (running) -> stopService -> onDestroy 이다 
 * 기존에 onStart도 있었지만, 구글에서 지양하고 있다.
 * 
 * 현재 작동기능 : 사진 업로드 */
public class UploaderService extends Service implements OnChangeNetworkStatusListener{
	private static final String TAG = "UPLOADER SERVICE";
	private static boolean isWiFi = false;
	private UploadThread uploadThread;

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

	/**wifi환경을 설정해주는 메소드 */
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

	
	/**데이터를 카우db에 보내도록 연결해주는 쓰레드 */
	class UploadThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				while (isWiFi) {
					Thread.sleep(500);
					ArrayList<Photo> arrFileList;
					// 쓰레드를 사용해서 돌려야 하나
					// 카메라 이벤트가 발생할 경우 파일 목록을 읽어온다
					PhotoStorage photoStrg = new PhotoStorage(getBaseContext());
					arrFileList = photoStrg.getImagesInfo();
					if (!arrFileList.isEmpty()) {

						CouchDBConnector couchDBTask = new CouchDBConnector(
								"photo", "post", "insert-photo");
						couchDBTask.setContext(getApplication());
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

	@Override
	public void OnChanged(int status) {
		// TODO Auto-generated method stub
		
	}

}
