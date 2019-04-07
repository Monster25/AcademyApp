package com.application.academy;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editable;
    String shit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Message");

        FriendlyMessage message = new FriendlyMessage("Shit");
        myRef.push().setValue(message);
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendlyMessage friendlyMessage = new FriendlyMessage(editable.getText().toString());
                 myRef.push().setValue(friendlyMessage);
                //Clear
                editable.setText("");
            }
        });*/

    }

}
