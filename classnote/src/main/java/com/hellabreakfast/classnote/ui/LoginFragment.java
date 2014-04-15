package com.hellabreakfast.classnote.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hellabreakfast.classnote.R;
import com.hellabreakfast.classnote.auth.GatechAccountAuthenticator;
import com.hellabreakfast.classnote.model.AsyncResultHandler;
import com.hellabreakfast.classnote.model.TSquareAPI;

public class LoginFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        Button saveButton = (Button) rootView.findViewById(R.id.loginButtonFragment);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText usernameField = (EditText) getActivity().findViewById(R.id.usernameFieldFragment);
                EditText passwordField = (EditText) getActivity().findViewById(R.id.passwordFieldFragment);


                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

        return rootView;
    }

}
