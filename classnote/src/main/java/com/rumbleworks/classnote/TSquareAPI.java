package com.rumbleworks.classnote;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSquareAPI {
    public static BasicCookieStore cookieStore = new BasicCookieStore();
    private static final String BASE_URL = "https://t-square.gatech.edu/direct";

    public static void login(final String username, final String password, final JsonHttpResponseHandler handler) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create a new HttpClient and Post Header
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("https://t-square.gatech.edu/direct/session");
                    HttpContext httpContext = new BasicHttpContext();

                    httpContext.setAttribute(ClientContext.COOKIE_STORE, TSquareAPI.cookieStore);

                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("_username", username));
                    nameValuePairs.add(new BasicNameValuePair("_password", password));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost, httpContext);
                    String responseStr = EntityUtils.toString(response.getEntity());
                    if (response.getStatusLine().getStatusCode() == 201) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("session_id", responseStr);
                        if (handler != null) {
                            handler.onSuccess(jsonObject);
                        }
                    } else {
                        if (handler != null) handler.onFailure();
                    }
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                } catch (JSONException e) {}
            }
        });
        thread.start();
    }

    // eventually use this to see if the users session has expired
    public static void getSession(final JsonHttpResponseHandler handler) {
        get("/session", handler);
    }

    public static void get(final String resource, final JsonHttpResponseHandler handler) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpContext httpContext = new BasicHttpContext();

                    httpContext.setAttribute(ClientContext.COOKIE_STORE, TSquareAPI.cookieStore);

                    HttpRequestBase request = new HttpGet(BASE_URL+resource+".json");
                    HttpResponse response = httpClient.execute(request, httpContext);
                    String responseStr = EntityUtils.toString(response.getEntity());
                    if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
                        if (handler != null) {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            handler.onSuccess(jsonObject);
                        }
                    } else {
                        if (handler != null) handler.onFailure();
                    }
                } catch (Exception e) {}

            }
        });
        thread.start();
    }
}


//https://t-square.gatech.edu/direct/site/gtc-0bb4-a1ee-5d18-a027-8c20b42c1703/.json
//gets a json format of all classes i have ever been in

//https://t-square.gatech.edu/portal/tool/3f4094d2-857f-4585-8e1d-b153a8690724?itemReference=/announcement/msg/gtc-cb8b-5158-5600-8483-86ad218d680a/main/