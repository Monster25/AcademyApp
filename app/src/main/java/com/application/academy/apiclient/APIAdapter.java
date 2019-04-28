package com.application.academy.apiclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIAdapter {
    private String retriever;
    private JSONObject object;
    private String temp;

    public String getTemp (String jsonString) throws JSONException {
        JSONObject root = null;
        try {
            root = new JSONObject(jsonString);
            JSONObject main = root.getJSONObject("main");
            temp = main.getString("temp");
            //temp = object.getString("temp");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return temp;
    }
}
