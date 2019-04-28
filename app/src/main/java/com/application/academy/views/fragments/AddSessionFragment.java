package com.application.academy.views.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.application.academy.R;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.application.academy.viewmodel.StudentViewModel;
import com.application.academy.views.activities.DateActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddSessionFragment extends Fragment {

    private Spinner studentSpinner;
    private Spinner timeSpinner;
    private Button addButton, cancelButton;
    private StudentViewModel viewModel;
    private ArrayList<CharSequence> studentNames;
    private ArrayList<Integer> studentIds;
    private StudentList studentList;
    private View view2;
    private int lastId;
    private Map<String, Integer> studentMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.add_session, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        view2 = view;
        //Set view model
        viewModel = ((DateActivity) getActivity()).getViewModel();
        //Observe student data and create student spinner based on that data.
        LiveData<Student> studentLiveData = viewModel.getStudentLiveData();

        studentLiveData.observe(this, new Observer<Student>()
        {
            public void onChanged(@Nullable Student student) {
                studentList = viewModel.getStudentList();
                if (studentList != null) {
                    studentMap = new HashMap<>();
                    studentNames = new ArrayList<>();
                   // studentIds = new ArrayList<>();
                    for (int i = 0; i < studentList.size(); i++) {
                        studentMap.put(studentList.getStudent(i).getName(), studentList.getStudent(i).getId());
                        studentNames.add(studentList.getStudent(i).getName());
                       // studentNames.add(studentList.getStudent(i).getName());
                       // studentIds.add(studentList.getStudent(i).getId());
                    }


                    //STUDENT SPINNER
                    //Create array adapter
                    ArrayAdapter<CharSequence> studentAdapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_spinner_dropdown_item, studentNames);
                    //Specify layout
                    studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Apply to adapter
                    studentSpinner.setAdapter(studentAdapter);
                    studentSpinner = view2.findViewById(R.id.studentSpinner);
                }
            }
                });




        studentSpinner = view.findViewById(R.id.studentSpinner);
        timeSpinner = view.findViewById(R.id.timeSpinner);
        //Transfer student list to spinner



//TIME SPINNER
        //Create array adapter
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.time_array, android.R.layout.simple_spinner_dropdown_item);
        //Specify layout
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply to adapter
        timeSpinner.setAdapter(timeAdapter);

        addButton = view.findViewById(R.id.setSessionButton);
        cancelButton = view.findViewById(R.id.cancelButton);




        //add session

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (studentSpinner.getChildCount() <= 0)
                {
                    Toast toast = Toast.makeText(getActivity(),"Please add a student first!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                    toast.show();
                }
                else if (timeSpinner.getSelectedItem().toString().matches(""))
                {
                    Toast toast = Toast.makeText(getActivity(), "Please select a time.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                    toast.show();
                }
                else {
                    try {
                        lastId = viewModel.getSessionList(DateActivity.getInstance().getDate()).getLastSession().getId()+1;
                    } catch (Exception e) {
                        lastId = 0;
                    }

                    viewModel.addSession(lastId, timeSpinner.getSelectedItem().toString(), DateActivity.getInstance().getDate(), Integer.parseInt(studentMap.get(studentSpinner.getSelectedItem().toString()).toString()));

                    //Unfade background and remove current fragment
                    ((DateActivity) getActivity()).unFadeBackground();
                    getFragmentManager().beginTransaction().remove(AddSessionFragment.this).commit();

                }
            }
        });

        //cancel student
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                ((DateActivity) getActivity()).unFadeBackground();
                getFragmentManager().beginTransaction().remove(AddSessionFragment.this).commit();

            }
        });

    }
}
