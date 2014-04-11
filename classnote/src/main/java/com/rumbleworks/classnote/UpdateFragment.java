package com.rumbleworks.classnote;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class UpdateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_update, container, false);

        Button saveButton = (Button)rootView.findViewById(R.id.loginButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText usernameField = (EditText)getActivity().findViewById(R.id.usernameField);
                EditText passwordField = (EditText)getActivity().findViewById(R.id.passwordField);


                TSquareAPI.login(usernameField.getText().toString(), passwordField.getText().toString(), new AsyncResultHandler() {
                    @Override
                    public void onSuccess() {
                          TSquareAPI.refreshAll();
//                        Intent intent = new Intent();
//                        intent.putExtra("username", username);
//                        intent.setClass(UpdateActivity.this, CompassActivity.class);
//
//                        startActivity(intent);
//                        finish();


                        Log.v( "cat", "cat worked");
                    }

                    @Override
                    public void onFailure() {
                        Log.v( "cat", "cat failed");
                    }
                });

            }
        });

        return rootView;
    }

}
