package com.rumbleworks.classnote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnnouncementListFragment extends Fragment {
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

        return rootView;
    }

    public void refreshAnnouncements() {
        AsyncHttpClient client = new AsyncHttpClient();

        //Execute get request using asynchttpclient for announcements 50 days old and 20 in number
        client.get(TSquareAPI.BASE_URL + "/announcement/user.json?d=50&n=20", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    JSONArray ann = res.getJSONArray("announcement_collection");
                    int len = ann.length();

                    //Need to trim the message to remove tags
                    for (int i = 0; i < len; i++) {
                        Log.d("Ann", stripHtml(ann.getJSONObject(i).getString("body")));
                        Datamart.getInstance().addAnnouncement((stripHtml(ann.getJSONObject(i).getString("body"))));
                    }
                }
                catch (JSONException e) {

                }
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