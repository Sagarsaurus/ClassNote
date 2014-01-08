package com.olivergoldbart.classnote;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void login( View view ) {
	    
 		Intent intent = new Intent();
 						
 		intent.setClass(MainActivity.this, LoginActivity.class);

		startActivity(intent);
		finish();
					
    }
	
	public void list( View view ) {
	    
 		Intent intent = new Intent();
 						
 		intent.setClass(MainActivity.this, ListActivity.class);

		startActivity(intent);
		finish();
					
    }
	

}
