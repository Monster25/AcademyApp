package com.application.academy.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.HashMap;

public class FirebaseQueryLiveData extends LiveData<DataSnapshot>
{
    private static final String LOG_TAG = "FirebaseQueryLiveData";

    private final Query query;
    private String value = null;
    private String childKey = "";
    private final MyValueEventListener listener = new MyValueEventListener();

    public FirebaseQueryLiveData(Query query)
    {
        this.query = query;
    }

    public FirebaseQueryLiveData(DatabaseReference database)
    {
        this.query = database;
    }

    public FirebaseQueryLiveData(DatabaseReference database, String value) {
        this.query = database;
        this.value = value;
    }
    @Override
    protected void onActive()
    {
        Log.d(LOG_TAG, "onActive");
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive()
    {
        Log.d(LOG_TAG, "onInactive");
        query.removeEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {

            setValue(dataSnapshot);

        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            Log.e(LOG_TAG, "Can't listen to query"+query, databaseError.toException());
        }
    }

    public void getChildKeyByValue(String orderBy, int equalTo, final OnGetDataListener listener)
    {
        listener.onStart();
        childKey = "";
        //Use interface in query to make sure value is updated AFTER asynchronous onDataChange is finished.
        query.orderByChild(orderBy).equalTo(equalTo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren())
                {
                   // Log.i(LOG_TAG, childSnapshot.getKey());
                    childKey = childSnapshot.getKey();
                    listener.onSuccess(childKey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    listener.onFailed(databaseError);
            }
        });
    }

    //Return the actual key
    public String getChildKey()
    {
        return childKey;
    }


}
