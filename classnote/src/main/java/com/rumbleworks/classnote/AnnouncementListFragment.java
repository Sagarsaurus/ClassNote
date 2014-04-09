package com.rumbleworks.classnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class AnnouncementListFragment extends ListFragment implements Observer {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public AnnouncementListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_announcement_list, container, false);

        if ( Datamart.getInstance().getVisited()[0] == false ) {
            Datamart.getInstance().setVisited(0, true);
            Intent intent = new Intent();
            intent.setClass(getActivity(), OverlayActivity.class);
            startActivity(intent);
        }
        setListAdapter(new AnnouncementAdapter(Datamart.getInstance().getAnnouncements(), getActivity()));
        return rootView;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Datamart.getInstance().addObserver(this);
    }

    public void onDestroy() {
        super.onDestroy();
        Datamart.getInstance().deleteObserver(this);
    }

    public void update(Observable observable, Object data) {
        setListAdapter(new AnnouncementAdapter(Datamart.getInstance().getAnnouncements(), getActivity()));
    }

}