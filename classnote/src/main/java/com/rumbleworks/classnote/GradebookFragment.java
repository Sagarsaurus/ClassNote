package com.rumbleworks.classnote;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GradebookFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    public GradebookFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gradebook, container, false);
        //TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
        //dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

        getSites();

        if ( Datamart.getInstance().getVisited()[3] == false ) {
            Datamart.getInstance().setVisited(3, true);
            Intent intent = new Intent();
            intent.setClass(getActivity(), OverlayActivity.class);
            startActivity(intent);
            //finish();
        }

        return rootView;
    }

    public void getSites() {
        AsyncHttpClient client = new AsyncHttpClient();
        //HttpContext httpContext = Datamart.getInstance().getHttpContext();
        //Log.d("Cookie Store", httpContext.toString());
        client.setCookieStore(TSquareAPI.cookieStore);

        //Execute get request using asynchttpclient for announcements 50 days old and 20 in number
        client.get(TSquareAPI.BASE_URL + "/site/gtc-0bb4-a1ee-5d18-a027-8c20b42c1703/.json", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject siteObject = new JSONObject(response);
                    JSONArray siteArray = siteObject.getJSONArray("site_collection");
                    int len = siteArray.length();
                    JSONObject temp;

                    //Need to trim the message to remove tags
                    for (int i = 0; i < len; i++) {

                        temp = siteArray.getJSONObject(i);

                        if ( temp != null ) {
                            Datamart.getInstance().addPropsObject(temp);

//                            Datamart.getInstance().addCourse(
//                                    ann.getJSONObject(i).getString("title"),
//                                    (stripHtml(ann.getJSONObject(i).getString("body"))),
//                                    new Date(ann.getJSONObject(i).getLong("createdOn")),
//                                    ann.getJSONObject(i).getString("siteTitle")
//                            );

                            Datamart.getInstance().addCourse( new Course( "test", "test", i ) );

                        }
                    }

                }
                catch (JSONException e) {

                }
            }

            @Override
            public void onFinish() {
                //setListAdapter(new AnnouncementAdapter(Datamart.getInstance().getAnnouncements(), getActivity()));
                Log.v( "cat", "cats actually goddamn finished" + Datamart.getInstance().getCourseList().size());
            }

        });
    }


}