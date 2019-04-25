package com.application.academy.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

//Interface to get key from database respecting asynchronous design
public interface OnGetDataListener {
    public void onStart();
    public void onSuccess(String childKey);
    public void onFailed(DatabaseError databaseError);
}
