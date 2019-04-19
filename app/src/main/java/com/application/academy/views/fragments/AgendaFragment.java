package com.application.academy.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.application.academy.firebase.FirebaseAdapter;
import com.application.academy.R;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.application.academy.viewmodel.StudentViewModel;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;

public class AgendaFragment extends Fragment {
    Button sendButton, removeButton, setButton;
    EditText studentName, sessionNumbers, studentId, studentName2, sessionNumbers2;
    CheckBox paid, paid2;
    TextView example, example2, example3;

    public static final String KEY_ITEM = "unique_key";
    public static final String KEY_INDEX = "index_key";
    private String mTime;

    private StudentList studentList;
    private int lastId = -1;
    private FirebaseAdapter adapter;
    private StudentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.agenda_fragment, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Interactable UI assignments
        studentName = view.findViewById(R.id.nameField);
        sessionNumbers = view.findViewById(R.id.sessionNumbers);
        paid = view.findViewById(R.id.paid);
        sendButton = view.findViewById(R.id.addButton);
        example = view.findViewById(R.id.example);
        example2 = view.findViewById(R.id.example2);
        example3 = view.findViewById(R.id.example3);
        removeButton = view.findViewById(R.id.removeButton);
        studentId = view.findViewById(R.id.studentId);
        setButton = view.findViewById(R.id.setButton);
        studentName2 = view.findViewById(R.id.nameField2);
        sessionNumbers2 = view.findViewById(R.id.sessionNumbers2);
        paid2 = view.findViewById(R.id.paid2);


        viewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        LiveData<Student> studentLiveData = viewModel.getStudentLiveData();

        studentLiveData.observe(this, new Observer<Student>()
        {
            public void onChanged(@Nullable Student student)
            {
                if (student!=null)
                {
                    studentList = viewModel.getStudentList();
                    lastId = studentList.getLastStudent().getId();

                    //button.setText(studentList.getStudent(0).getName());
                    // example.setText(studentList.getStudent(0).getName());
                    // example2.setText(studentList.getStudent(1).getName());
                    // example3.setText(studentList.getStudent(2).getName());
                    // example.setText(dataSnapshot.child("Student1").getValue(Student.class).getName());
                }
            }
        });
        //add student
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
