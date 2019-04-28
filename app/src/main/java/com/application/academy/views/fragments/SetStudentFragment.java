package com.application.academy.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.application.academy.R;
import com.application.academy.viewmodel.StudentViewModel;
import com.application.academy.views.activities.MainActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SetStudentFragment extends Fragment {


    public static final String KEY_ITEM = "unique_key";
    public static final String KEY_INDEX = "index_key";
    private String mTime;

    private StudentViewModel viewModel;
    private int id;
    private EditText studentName, sessions;
    private CheckBox paid;
    private Button setButton, cancelButton;
    private ImageView delete;
    private int sessionNumber, position;
    private String name;
    private boolean paidCheck;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        Bundle args = getArguments();
        name= args.getString("name", "Student");
        sessionNumber = args.getInt("sessions", 0);
        paidCheck = args.getBoolean("paid", false);
        position = args.getInt("position", 0);
        id = args.getInt("id", 0);
        return inflater.inflate(R.layout.setstudent_fragment, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        studentName = view.findViewById(R.id.nameField);
        sessions = view.findViewById(R.id.sessionNumbers);
        paid = view.findViewById(R.id.paid);
        setButton = view.findViewById(R.id.setStudentButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        delete = view.findViewById(R.id.deleteIcon);
        viewModel = ((MainActivity) getActivity()).getViewModel();

        studentName.setText(name);
        sessions.setText(""+sessionNumber);
        paid.setChecked(paidCheck);


        //set student
        setButton.setOnClickListener(new View.OnClickListener() {
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

                    viewModel.setStudent(studentName.getText().toString(), paid.isChecked(), Integer.parseInt(sessions.getText().toString()), id);
                    studentName.setText("");
                    paid.setChecked(false);
                    sessions.setText("");
                    //Hide Keyboard
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                //Unfade background and remove current fragment
                    ((MainActivity) getActivity()).unFadeBackground();
                    getFragmentManager().beginTransaction().remove(SetStudentFragment.this).commit();
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
                getFragmentManager().beginTransaction().remove(SetStudentFragment.this).commit();

            }
        });

        //Remove student
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass id to query database
                viewModel.removeStudent(id);
                // myRef.child("Student"+studentId.getText()).removeValue();
                //Hide Keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                ((MainActivity) getActivity()).unFadeBackground();
                getFragmentManager().beginTransaction().remove(SetStudentFragment.this).commit();
            }
        });
    }



}
