package com.hellabreakfast.classnote.ui;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hellabreakfast.classnote.model.AsyncResultHandler;
import com.hellabreakfast.classnote.R;
import com.hellabreakfast.classnote.model.TSquareAPI;
import com.hellabreakfast.classnote.auth.GatechAccountAuthenticator;

public class LoginActivity extends AccountAuthenticatorActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
    }

	public void login( View view ) {
        EditText usernameField = (EditText)findViewById(R.id.usernameField);
        EditText passwordField = (EditText)findViewById(R.id.passwordField);
        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();


        TSquareAPI.login(usernameField.getText().toString(), passwordField.getText().toString(), new AsyncResultHandler() {
            @Override
            public void onSuccess() {

                String accountType = GatechAccountAuthenticator.ACCOUNT_TYPE;
                AccountManager accMgr = AccountManager.get(LoginActivity.this);
                final Account account = new Account(username, accountType);
                accMgr.addAccountExplicitly(account, password, null);

                // Now we tell our caller, could be the Andreoid Account Manager or even our own application
                // that the process was successful

                final Intent intent = new Intent();
                intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
                intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);
                LoginActivity.this.setAccountAuthenticatorResult(intent.getExtras());
                LoginActivity.this.setResult(RESULT_OK, intent);


                Intent mainIntent = new Intent();
                mainIntent.setClass(LoginActivity.this, MainActivity.class);

                startActivity(mainIntent);
                finish();
            }

            @Override
            public void onFailure() {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        });

	}

}
