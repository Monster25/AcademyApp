package com.application.academy;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity {

    Button sendButton, removeButton, setButton;
    EditText studentName, sessionNumbers, studentId, studentName2, sessionNumbers2;
    CheckBox paid, paid2;
    TextView example, example2, example3;
    //Exclude actual incrementer from database
    @Exclude
    public final static AtomicInteger incrementer = new AtomicInteger(-1); //id incrementer

    private StudentList studentList;
    private int lastId = -1;
    private StudentList students;
    private FirebaseAdapter adapter;
    private StudentViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Interactable UI assignments
        studentName = findViewById(R.id.nameField);
        sessionNumbers = findViewById(R.id.sessionNumbers);
        paid = findViewById(R.id.paid);
        sendButton = findViewById(R.id.addButton);
        example = findViewById(R.id.example);
        example2 = findViewById(R.id.example2);
        example3 = findViewById(R.id.example3);
        removeButton = findViewById(R.id.removeButton);
        studentId = findViewById(R.id.studentId);
        setButton = findViewById(R.id.setButton);
        studentName2 = findViewById(R.id.nameField2);
        sessionNumbers2 = findViewById(R.id.sessionNumbers2);
        paid2 = findViewById(R.id.paid2);

        viewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        LiveData<Student> studentLiveData = viewModel.getStudentLiveData();
        adapter = FirebaseAdapter.getInstance();

        studentLiveData.observe(this, new Observer<Student>()
        {
            public void onChanged(@Nullable Student student)
            {
                if (student!=null)
                {
                    studentList = viewModel.getStudentList();
                    lastId = studentList.getLastStudent().getId();

                    example.setText(studentList.getStudent(0).getName());
                  // example.setText(studentList.getStudent(0).getName());
                   // example2.setText(studentList.getStudent(1).getName());
                   // example3.setText(studentList.getStudent(2).getName());
                   // example.setText(dataSnapshot.child("Student1").getValue(Student.class).getName());
                }
            }
        });

        //Writing to the database v

        // FirebaseDatabase database = FirebaseDatabase.getInstance();
         //final DatabaseReference myRef = database.getReference("Students");

        //Add Student
         sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Student student = new Student(studentName.getText().toString(), paid.isChecked(), Integer.parseInt(sessionNumbers.getText().toString()), lastId+1);
                viewModel.addStudent(studentName.getText().toString(), paid.isChecked(), Integer.parseInt(sessionNumbers.getText().toString()), lastId+1);
                //myRef.child("Student"+student.getId()).setValue(student);
                studentName.setText("");
                paid.setChecked(false);
                sessionNumbers.setText("");
            }
        });


         //Remove student
         removeButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 viewModel.removeStudent("Student"+studentId.getText().toString());
               // myRef.child("Student"+studentId.getText()).removeValue();
                studentId.setText("");
             }
        });

         //Set Student
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int id = Integer.parseInt(studentId.getText().toString());
                viewModel.setStudent(studentName2.getText().toString(), paid2.isChecked(), Integer.parseInt(sessionNumbers2.getText().toString()), Integer.parseInt(studentId.getText().toString()));
                //Student student = new Student(studentName2.getText().toString(), paid2.isChecked(), Integer.parseInt(sessionNumbers2.getText().toString()), id);
               // myRef.child("Student"+studentId.getText()).setValue(student);
                studentId.setText("");
                studentName2.setText("");
                paid2.setChecked(false);
                sessionNumbers2.setText("");
            }
        });




    }



}
