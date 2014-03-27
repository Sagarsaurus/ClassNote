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

public class AnnouncementListFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public AnnouncementListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_announcement_list, container, false);
        //TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
        //dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        //View topLevelLayout = rootfindViewById(R.id.top_layout);
        //topLevelLayout.setVisibility(View.INVISIBLE);
        refreshAnnouncements();

        if ( Datamart.getInstance().getVisited()[0] == false ) {
            Datamart.getInstance().setVisited(0, true);
            Intent intent = new Intent();
            intent.setClass(getActivity(), OverlayActivity.class);
            startActivity(intent);
            //finish();
        }
        setListAdapter(new AnnouncementAdapter(Datamart.getInstance().getAnnouncements(), getActivity()));

        return rootView;
    }

    public void refreshAnnouncements() {
        AsyncHttpClient client = new AsyncHttpClient();
        //HttpContext httpContext = Datamart.getInstance().getHttpContext();
        //Log.d("Cookie Store", httpContext.toString());
        client.setCookieStore(TSquareAPI.cookieStore);

        //Execute get request using asynchttpclient for announcements 50 days old and 20 in number
        client.get(TSquareAPI.BASE_URL + "/announcement/user.json?d=50&n=25", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    JSONArray ann = res.getJSONArray("announcement_collection");
                    int len = ann.length();

                    //Need to trim the message to remove tags
                    for (int i = 0; i < len; i++) {
                        Datamart.getInstance().addAnnouncement(
                                ann.getJSONObject(i).getString("title"),
                                (stripHtml(ann.getJSONObject(i).getString("body"))),
                                new Date(ann.getJSONObject(i).getLong("createdOn")),
                                ann.getJSONObject(i).getString("siteTitle")
                        );
                    }
                }
                catch (JSONException e) {

                }
            }

            @Override
            public void onFinish() {
                setListAdapter(new AnnouncementAdapter(Datamart.getInstance().getAnnouncements(), getActivity()));
            }

        });
    }

    /**
     * Stripping html tags from the message
     * @param html
     * @return
     */
    public static String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }
}