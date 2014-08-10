package com.tleaf.lifelog.util;

import android.os.AsyncTask;
import com.tleaf.lifelog.model.Documentation;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.android.http.AndroidHttpClient;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.net.MalformedURLException;

/**
 * Created by jangyoungjin on 8/10/14.
 */
public class DBconnector extends AsyncTask<String, String, String> {
    private static final String TAG = "카우치 디비 통신";
    private static final String URL = "http://54.191.147.237:5984";
    private static final String USER_NAME = "couchdb";
    private static final String USER_PASSWORD = "dudwls";
    private static final String DATABASE = "test";


    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient client = new AndroidHttpClient.Builder()
                    .url(URL)
                    .username(USER_NAME)
                    .password(USER_PASSWORD)
                    .build();

            CouchDbInstance dbInstance = new StdCouchDbInstance(client);
            CouchDbConnector db = dbInstance.createConnector(DATABASE, true);

            Documentation documentation = new Documentation();
            documentation.setData("richard");

            db.create(documentation);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
