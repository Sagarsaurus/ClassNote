package com.hellabreakfast.classnote.model;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.text.Html;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.hellabreakfast.classnote.ui.ClassNoteApp;
import com.hellabreakfast.classnote.auth.GatechAccountAuthenticator;
import com.hellabreakfast.classnote.ui.UpdatePasswordActivity;

import org.apache.http.impl.client.BasicCookieStore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * This class handles all of our interaction with the T-Square API thorugh static methods that login and refresh data.
 */
public class TSquareAPI {
    // This cookie store holds the cookie that we get back when we login.
    public static BasicCookieStore cookieStore = new BasicCookieStore();
    public static final String BASE_URL = "https://t-square.gatech.edu/direct";

    /**
     * Send a POST request to /direct/session with the specified credentials. Call the appropriate method of the handler on completion.
     *
     * @param username
     * @param password
     * @param handler The handler to be called with a success or failure message
     */
    public static void login(final String username, final String password, final AsyncResultHandler handler) {
        // Create a new HttpClient and Post Header
        AsyncHttpClient client = new AsyncHttpClient();
        client.setCookieStore(TSquareAPI.cookieStore);
        client.post(TSquareAPI.BASE_URL+"/session", new RequestParams("_username", username, "_password", password), new AsyncHttpResponseHandler() {
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                if (statusCode == 201) {
                    if (handler != null) handler.onSuccess();
                    Datamart.getInstance().setUsername(username);
                    Datamart.getInstance().setOffline(false);
                } else {
                    if (handler != null) handler.onFailure();
                }
            }
        });
    }

    /**
     * Loops through the courses and refreshes the list of announcements for each course and inserts the new data into the Datamart
     */
    public static void refreshAnnouncements() {
        for (Course c : Datamart.getInstance().getCourseList()) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setCookieStore(TSquareAPI.cookieStore);

            //Execute get request using asynchttpclient for announcements 50 days old and 20 in number
            final Course course = c;
            client.get(TSquareAPI.BASE_URL + "/announcement/site/"+c.getSiteId()+".json", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject res = new JSONObject(new String(responseBody));
                        JSONArray ann = res.getJSONArray("announcement_collection");
                        int len = ann.length();

                        //Need to trim the message to remove tags
                        for (int i = 0; i < len; i++) {
                            Announcement announcement = new Announcement(
                                    ann.getJSONObject(i).getString("id"),
                                    ann.getJSONObject(i).getString("title"),
                                    ann.getJSONObject(i).getString("createdByDisplayName"),
                                    (stripHtml(ann.getJSONObject(i).getString("body"))),
                                    false,
                                    new Date(ann.getJSONObject(i).getLong("createdOn")));
                            if (!course.hasAnnouncement(announcement)) {
                                course.addAnnouncement(announcement);
                            }
                        }
                        Datamart.getInstance().save();
                    } catch (JSONException e) {

                    }
                }

            });
        }
    }

    /**
     * Loops through the courses and refreshes the list of assignments for each course and inserts the new data into the Datamart
     */
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
                            JSONObject content = assObject.optJSONObject("content");
                            //Log.d("Content: ", content.toString());

                            Assignment assignment = new Assignment(assObject.optString("id"),assObject.optString("title"),stripHtml(content.optString("instructions")),false, dueDate);

                            course.addAssignment(assignment);
                        }
                        Datamart.getInstance().save();
                    } catch (JSONException e) {}
                }
            });
        }
    }


    /**
     * First refresh the list of courses from T-Square (making sure to only get courses for this semester), and then refresh the assignments and announcements.
     */
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

                    if (siteArray.length() == 0) {
                        tryReauth();
                        return;
                    }

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
                    Datamart.getInstance().setLastRefreshed(new Date());
                    Datamart.getInstance().save();

                    TSquareAPI.refreshAnnouncements();
                    TSquareAPI.refreshAssignments();
                }
                catch (JSONException e) {

                }
            }

        });
    }

    /**
     * This method is called if our cookie expires. It retrieves the password from the AccountManager
     * and logs in again to get a new cookie. Once it gets the cookie, it refreshes.
     */
    public static void tryReauth() {
        try {
            AccountManager accountManager = AccountManager.get(ClassNoteApp.getApplication().getApplicationContext());
            Account account = new Account(Datamart.getInstance().getUsername(), GatechAccountAuthenticator.ACCOUNT_TYPE);
            String password = accountManager.getPassword(account);
            login(Datamart.getInstance().getUsername(), password, new AsyncResultHandler() {
                @Override
                public void onSuccess() {
                    refreshAll();
                }

                @Override
                public void onFailure() {
                    requirePasswordUpdateToRefresh();
                }
            });
        } catch (Exception e) {
            requirePasswordUpdateToRefresh();
        }
    }

    /**
     * Start the password update activity (because the user has changed their T-Square password)
     */
    public static void requirePasswordUpdateToRefresh() {
        Intent intent = new Intent(ClassNoteApp.getApplication().getApplicationContext(), UpdatePasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        ClassNoteApp.getApplication().startActivity(intent);
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
