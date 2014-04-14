package com.hellabreakfast.classnote.ui;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.hellabreakfast.classnote.model.AsyncResultHandler;
import com.hellabreakfast.classnote.model.Datamart;
import com.hellabreakfast.classnote.R;
import com.hellabreakfast.classnote.model.TSquareAPI;
import com.hellabreakfast.classnote.auth.GatechAccountAuthenticator;

public class UpdatePasswordActivity extends AccountAuthenticatorActivity {
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
                .setTitle("T-Square Password Invalid")
                .setMessage("Enter your correct T-Square password to refresh")
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
        final String username = Datamart.getInstance().getUsername();
        final String password = passwordField.getText().toString();

        TSquareAPI.login(username, password, new AsyncResultHandler() {
            @Override
            public void onSuccess() {

                String accountType = GatechAccountAuthenticator.ACCOUNT_TYPE;
                AccountManager accMgr = AccountManager.get(UpdatePasswordActivity.this);
                final Account account = new Account(username, accountType);
                accMgr.setPassword(account, password);

                final Intent intent = new Intent();
                intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
                intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);
                UpdatePasswordActivity.this.setAccountAuthenticatorResult(intent.getExtras());
                UpdatePasswordActivity.this.setResult(RESULT_OK, intent);

                finish();
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(UpdatePasswordActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG);
                toast.show();
            }
        });

	}

}
