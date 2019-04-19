package com.application.academy.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.academy.R;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.application.academy.viewmodel.StudentViewModel;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class StudentsFragment extends Fragment {

    RecyclerView studentsView;
    RecyclerView.Adapter studentsViewAdapter;

    public static final String KEY_ITEM = "unique_key";
    public static final String KEY_INDEX = "index_key";
    private String mTime;

    private StudentViewModel viewModel;
    private int lastId = -1;
    private StudentList studentList;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.students_fragment, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
       // button = view.findViewById(R.id.button);

        studentsView = view.findViewById(R.id.rv);

        studentsView.setHasFixedSize(true);

       // layoutManager = new LinearLayoutManager(this.getActivity());
        studentsView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


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

                     studentsViewAdapter = new StudentRecyclerAdapter(studentList);
                     studentsView.setAdapter(studentsViewAdapter);
                    // example.setText(studentList.getStudent(0).getName());
                    // example2.setText(studentList.getStudent(1).getName());
                    // example3.setText(studentList.getStudent(2).getName());
                    // example.setText(dataSnapshot.child("Student1").getValue(Student.class).getName());
                }
            }
        });

      //  ArrayList<Student> students = new ArrayList<>();
       // students.add(new Student("Shize", true, 123,3 ));





       // studentList.addStudent(new Student("gadaf", true, 222, 2));
        //studentsViewAdapter = new StudentRecyclerAdapter(studentList);
        //studentsView.setAdapter(studentsViewAdapter);
    }


}
