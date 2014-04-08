package com.rumbleworks.classnote;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

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
        EditText usernameField = (EditText)findViewById(R.id.usernameField);
        EditText passwordField = (EditText)findViewById(R.id.passwordField);
        final String username = usernameField.getText().toString();


        TSquareAPI.login(usernameField.getText().toString(), passwordField.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject object) {

                Intent intent = new Intent();
                intent.putExtra("username", username);
                intent.setClass(UpdateActivity.this, CompassActivity.class);

                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure() {
                UpdateActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(UpdateActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        });

	}



}
