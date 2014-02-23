package com.rumbleworks.classnote;

import org.json.JSONObject;

public interface JsonHttpResponseHandler {

    public void onSuccess(JSONObject object);
    public void onFailure();

}
