package com.tleaf.lifelog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tleaf.lifelog.R;

public class StartActivity extends Activity {
	
	Button startbtnmap;
	Button startbtnfacebook;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		startbtnmap = (Button)findViewById(R.id.startbtnmap);
		startbtnmap.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity( new Intent( StartActivity.this, MapViewActivity.class ) );
			}
		} );
		
		startbtnfacebook = (Button)findViewById(R.id.startbtnfacebook);
		startbtnfacebook.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity( new Intent( StartActivity.this, MainActivity.class ) );
			}
		});
		
	}

}