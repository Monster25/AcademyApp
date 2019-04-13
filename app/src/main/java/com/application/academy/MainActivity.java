package com.application.academy;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity {

    Button sendButton;
    EditText studentName;
    EditText sessionNumbers;
    CheckBox paid;
    TextView example;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Interactable UI assignments
        studentName = findViewById(R.id.nameField);
        sessionNumbers = findViewById(R.id.sessionNumbers);
        paid = findViewById(R.id.paid);
        sendButton = findViewById(R.id.button);
        example = findViewById(R.id.example);

        StudentViewModel viewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        LiveData<Student> studentLiveData = viewModel.getStudentLiveData();

        studentLiveData.observe(this, new Observer<Student>()
        {
            public void onChanged(Student student)
            {
                if (student != null)
                {
                    //Update the UI with values from the snapshot
                    //String text = dataSnapshot.child("s").getValue(String.class);

                    example.setText(student.getName());
                }
            }

        });

        //Writing to the database v

         FirebaseDatabase database = FirebaseDatabase.getInstance();
         final DatabaseReference myRef = database.getReference("Students");

         sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student(studentName.getText().toString(), paid.isChecked(), Integer.parseInt(sessionNumbers.getText().toString()));
                myRef.child("Student"+Integer.toString(student.getId())).setValue(student);
                studentName.setText("");
                paid.setChecked(false);
                sessionNumbers.setText("");
            }
        });

    }



}
