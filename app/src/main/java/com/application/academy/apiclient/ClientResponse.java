package com.application.academy.apiclient;

import com.google.firebase.database.DatabaseError;

import org.json.JSONException;

public interface ClientResponse {

    public void onResponseReceived(String result) throws JSONException;
    public void onFailed(DatabaseError databaseError);
}
