package com.application.academy.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.application.academy.R;
import com.application.academy.viewmodel.StudentViewModel;
import com.application.academy.views.MainActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class AddStudentFragment extends Fragment {


    public static final String KEY_ITEM = "unique_key";
    public static final String KEY_INDEX = "index_key";
    private String mTime;

    private StudentViewModel viewModel;
    private int lastId;
    private EditText studentName, sessions;
    private CheckBox paid;
    private Button addButton, cancelButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.addstudent_fragment, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        studentName = view.findViewById(R.id.nameField);
        sessions = view.findViewById(R.id.sessionNumbers);
        paid = view.findViewById(R.id.paid);
        addButton = view.findViewById(R.id.addStudentButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        viewModel = ((MainActivity) getActivity()).getViewModel();


        //add student
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentName.getText().toString().matches(""))
                {
                    Toast.makeText(getActivity(),"Please enter a name.", Toast.LENGTH_SHORT).show();
                }
                else if (sessions.getText().toString().matches(""))
                {
                    Toast.makeText(getActivity(), "Please enter a number of sessions.", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        lastId = viewModel.getStudentList().getLastStudent().getId();
                    } catch (Exception e) {
                        lastId = -1;
                    }

                    viewModel.addStudent(studentName.getText().toString(), paid.isChecked(), Integer.parseInt(sessions.getText().toString()), lastId + 1);
                    studentName.setText("");
                    paid.setChecked(false);
                    sessions.setText("");
                    //Hide Keyboard
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                //Unfade background and remove current fragment
                    ((MainActivity) getActivity()).unFadeBackground();
                    getFragmentManager().beginTransaction().remove(AddStudentFragment.this).commit();
                }
            }
        });

        //cancel student
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //Hide Keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                ((MainActivity) getActivity()).unFadeBackground();
                getFragmentManager().beginTransaction().remove(AddStudentFragment.this).commit();

            }
        });
    }



}
