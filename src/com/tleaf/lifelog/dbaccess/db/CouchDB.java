package com.tleaf.lifelog.dbaccess.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.android.http.AndroidHttpClient;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import android.content.Context;
import android.os.AsyncTask;

import com.tleaf.lifelog.dbaccess.DBListener;
import com.tleaf.lifelog.dbaccess.DBOption;
import com.tleaf.lifelog.model.Lifelog;
import com.tleaf.lifelog.model.Photo;

public class CouchDB implements DBInterface {
	private static final String TAG = "카우치 디비통신";
	// 카우치디비 옵션
	private static final String URL = "http://54.191.147.237:5984";
	private static final String USER_NAME = "couchdb";
	private static final String USER_PASSWORD = "dudwls";
	// 접근옵션
	private DBOption option;
	private String method;
	private String dbName;
	private String url;
	// 클래스 로더
	private ClassLoader classLoader;
	// 액티비티 컨텍스트
	private Context context;
	// 콜백리스너
	private DBListener listener;
	// 사용자이름 ( 나중에 Preference에서 뽑는다 )
	private String userName = "jYoung";

	@Override
	public void execute(DBOption option) {
		// TODO Auto-generated method stub

	}

	private class DBtask extends AsyncTask<Lifelog, Void, String> {
		private CouchDbConnector db;

		@Override
		protected String doInBackground(Lifelog... data) {
			// TODO Auto-generated method stub
			String result = "false";

			// 카우치 디비를 초기화 시켜줍니다.
			init();

			if (method.equals("post")) {
				result = createPhoto((Photo) data[0]);
			} else if (method.equals("get")) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		// 카우치디비 초기화 메서드
		private void init() {
			HttpClient client = null;

			try {
				client = new AndroidHttpClient
						.Builder()
						.url(URL)
						.username(USER_NAME)
						.password(USER_PASSWORD)
						.build();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (client != null) {
				CouchDbInstance dbInstance = new StdCouchDbInstance(client);
				db = dbInstance.createConnector(dbName, true);
			}
		}

		// 파일을 추가한다.
		// issue : 여기서 메모리 용량을 많이 차지한다.
		private AttachmentInputStream addAttachmentStream(Photo photo) {
			String imagePath = photo.getImgPath();
			File file = new File(imagePath);

			InputStream is = null;
			try {
				is = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AttachmentInputStream attInput = new AttachmentInputStream(
					photo.getFileName(), is, "image/png");
			return attInput;
		}

		private String createPhoto(Photo photo) {
			String filePath = photo.getImgPath();
			if (filePath != null) {
				AttachmentInputStream attInput = addAttachmentStream(photo);
				db.createAttachment(photo.getFileName(), attInput);
				return "true";
			}
			return "false";
		}
	}
}
