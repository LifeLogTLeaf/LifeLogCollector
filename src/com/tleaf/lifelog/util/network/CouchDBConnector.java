package com.tleaf.lifelog.util.network;

import java.net.MalformedURLException;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.android.http.AndroidHttpClient;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import android.content.Context;
import android.os.AsyncTask;

import com.tleaf.lifelog.model.Document;
import com.tleaf.lifelog.util.Mylog;

/**
 * Created by jangyoungjin on 8/1/14.
 * 카우치 데이터베이스에 직접적으로 접속해서 데이터를 저장하는 쓰레드 클래스입니다.
 */
public class CouchDBConnector extends AsyncTask<Document, Void, String> {
    private static final String TAG = "카우치 디비 통신";
    private static final String URL = "http://54.191.147.237:5984";
    private static final String USER_NAME = "couchdb";
    private static final String USER_PASSWORD = "dudwls";

    private String http_method, request_name, db_name;
    private Context mContext;
    //private DBListener mDBListener;

    /**
     * 생성자
     *
     * @param dbName       : 데이터베이스 이름
     * @param method       : CRUD
     * @param DBListener   : 데이터 요청에대한 응답이 왔을때 이를 받을 객체.
     * @param request_name : 요청명 (실질적으로 디비에는 전송되지 않는다 )
     */
    public CouchDBConnector(String dbName, String method, String request_name) {
        this.db_name = dbName;
        this.http_method = method;
        //this.mDBListener = DBListener;
        this.request_name = request_name;
    }


    /*HTTP 통신을 사용하는 액티비티의 컨텍스트를 셋팅한다. */
    public void setContext(Context Context) {
        this.mContext = Context;
    }

    /**
     * 카우치디비에 직접저으로 데이터를 저장한다.
     *
     * @param db      : 카우치디비 연결에 사용할 객체.
     * @param lifeLog : 저장할 객체 데이터 ( JSON으로 시리얼라이제이션 된다.)
     * @return
     */
    public boolean requestPost(CouchDbConnector db, Document document) {
        db.create(document);

        if (document.getId() != null) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected String doInBackground(Document... data) {
        boolean result = false;
        try {
            HttpClient client = new AndroidHttpClient.Builder()
                    .url(URL)
                    .username(USER_NAME)
                    .password(USER_PASSWORD)
                    .build();

            CouchDbInstance dbInstance = new StdCouchDbInstance(client);
            CouchDbConnector db = dbInstance.createConnector(db_name, true);

            if (http_method.equals("post")) {
                result = requestPost(db, data[0]);
            } else if (http_method.equals("get")) {

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (result) {
            return "true";
        } else {
            return "false";
        }

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Mylog.i(TAG, "카우치디비 POST 완료 : " + result);
        //mDBListener.onResponse(result, request_name);
    }

}
