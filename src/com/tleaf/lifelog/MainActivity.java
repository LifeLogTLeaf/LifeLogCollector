package com.tleaf.lifelog;

import android.app.Activity;
import android.os.Bundle;

import com.tleaf.lifelog.util.DBconnector;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 테스트소스
        DBconnector dBconnector = new DBconnector();
        dBconnector.execute();

	}

}
