package com.rumbleworks.classnote;

import android.provider.ContactsContract;
import android.text.Html;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class TSquareAPI {
    public static BasicCookieStore cookieStore = new BasicCookieStore();
    public static final String BASE_URL = "https://t-square.gatech.edu/direct";

    public static void login(final String username, final String password, final AsyncResultHandler handler) {
        // Create a new HttpClient and Post Header
        AsyncHttpClient client = new AsyncHttpClient();
        client.setCookieStore(TSquareAPI.cookieStore);
        client.post(TSquareAPI.BASE_URL+"/session", new RequestParams("_username", username, "_password", password), new AsyncHttpResponseHandler() {
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                if (statusCode == 201) {
                    if (handler != null) handler.onSuccess();
                } else {
                    if (handler != null) handler.onFailure();
                }
            }
        });
    }

    public static void refreshAnnouncements() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setCookieStore(TSquareAPI.cookieStore);

        //Execute get request using asynchttpclient for announcements 50 days old and 20 in number
        client.get(TSquareAPI.BASE_URL + "/announcement/user.json?d=50&n=25", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                try {
                    JSONObject res = new JSONObject(new String(responseBody));
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
                    Datamart.getInstance().save();
                } catch (JSONException e) {

                }
            }

        });
    }

    public static void refreshAssignments() {
        for (Course c : Datamart.getInstance().getCourseList()) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setCookieStore(TSquareAPI.cookieStore);

            //Execute get request using asynchttpclient for announcements 50 days old and 20 in number
            final Course course = c;
            client.get(TSquareAPI.BASE_URL + "/assignment/site/"+c.getSiteId()+".json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                    try {
                        if (statusCode != 200) return;
                        JSONObject res = new JSONObject(new String(responseBody));
                        JSONArray assArray = res.getJSONArray("assignment_collection");
                        for (int i = 0; i < assArray.length(); i++) {
                            JSONObject assObject = assArray.optJSONObject(i);
                            JSONObject dueDateObject = assObject.optJSONObject("dueTime");
                            Date dueDate = null;
                            if (dueDateObject != null) {
                                long dueDateInt = dueDateObject.optLong("time");
                                dueDate = dueDateInt > 0 ? new Date(dueDateInt) : null;
                            }
                            Assignment assignment = new Assignment(assObject.optString("id"),assObject.optString("title"),assObject.optString("instructions"),false, dueDate);

                            course.addAssignment(assignment);
                        }
                        Datamart.getInstance().save();
                    } catch (JSONException e) {}
                }
            });
        }
    }


    // refresh sites and then refresh assignments and announcements when finished
    public static void refreshAll() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setCookieStore(TSquareAPI.cookieStore);

        //Execute get request using asynchttpclient for announcements 50 days old and 20 in number
        client.get(TSquareAPI.BASE_URL + "/site.json", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                try {
                    JSONObject siteObject = new JSONObject(new String(responseBody));
                    JSONArray siteArray = siteObject.getJSONArray("site_collection");

                    //Need to trim the message to remove tags
                    for (int i = 0; i < siteArray.length(); i++) {
                        JSONObject site = siteArray.optJSONObject(i);
                        JSONObject props = site.optJSONObject("props");
                        if (props == null) {
                            System.out.println("no props");
                            continue;
                        }
                        String term = props.optString("term");
                        if (term == null || !term.equals("SPRING 2014")) {
                            System.out.println("wrong term");
                            continue;
                        }

                        Course c = new Course(site.optString("title"), site.optString("entityId"));
                        Datamart.getInstance().addCourse(c);
                    }
                    Datamart.getInstance().save();
                }
                catch (Exception e) {

                }
            }

            @Override
            public void onFinish() {
                TSquareAPI.refreshAnnouncements();
                TSquareAPI.refreshAssignments();
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


//https://t-square.gatech.edu/direct/site/gtc-0bb4-a1ee-5d18-a027-8c20b42c1703/.json
//gets a json format of all classes i have ever been in

//https://t-square.gatech.edu/portal/tool/3f4094d2-857f-4585-8e1d-b153a8690724?itemReference=/announcement/msg/gtc-cb8b-5158-5600-8483-86ad218d680a/main/