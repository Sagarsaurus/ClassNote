package com.rumbleworks.classnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginToRefreshActivity extends Activity {
    private EditText passwordField;
    private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_to_refresh);
        passwordField = new EditText(this);
        passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordField.setHint("Password");
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Reenter T-Square Password to Refresh")
                .setView(passwordField)
                .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        attemptLogin();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create();
        dialog.show();
    }

	public void attemptLogin() {
        progressDialog = ProgressDialog.show(this, "Verifying Password", null, true, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                progressDialog.dismiss();
                Datamart.getInstance().setOffline(true);
                finish();
            }
        });
        TSquareAPI.login(Datamart.getInstance().getUsername(), passwordField.getText().toString(), new AsyncResultHandler() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(LoginToRefreshActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG);
                toast.show();
            }
        });

	}

}
