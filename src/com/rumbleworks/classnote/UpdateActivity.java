package com.rumbleworks.classnote;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class UpdateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_update);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update, menu);
		return true;
	}
	
	public void login( View view ) {

		Intent intent = new Intent();
			
		//arrayList.add(0, currentDishID);
		//intent.putCharSequenceArrayListExtra("arrayList", arrayList);
		
		//intent.putExtra( "currentRestaurantID", currentRestaurantID );
		//intent.putExtra( "currentFoodmenuID", currentFoodmenuID );
		//intent.putExtra( "currentDishID", currentDishID );
		
		intent.setClass(UpdateActivity.this, MainActivity.class);

		startActivity(intent);
		finish();
		
	
	}
}
