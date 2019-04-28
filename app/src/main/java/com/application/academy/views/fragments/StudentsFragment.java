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
import com.application.academy.views.activities.MainActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        studentsView = view.findViewById(R.id.rv);

        studentsView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        studentsView.setLayoutManager(manager);

        studentsView.addOnItemTouchListener(new StudentRecyclerItemClickListener(getContext(), studentsView, new StudentRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Fragment fragment = getFragmentManager().findFragmentById(R.id.addStudentFragmentPlaceholder);
                if (fragment instanceof SetStudentFragment);
                else
                {
                    ((MainActivity) getActivity()).fadeBackground();
                    fragment = new SetStudentFragment();
                    Bundle args = new Bundle();
                    args.putInt("position", position);
                    args.putString("name", studentList.getStudent(position).getName());
                    args.putInt("sessions", studentList.getStudent(position).getSessions());
                    args.putBoolean("paid", studentList.getStudent(position).getPaid());
                    args.putInt("id", studentList.getStudent(position).getId());
                    fragment.setArguments(args);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.addStudentFragmentPlaceholder, fragment);
                    ft.commit();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        manager.setStackFromEnd(true);

        viewModel = ((MainActivity) getActivity()).getViewModel();

        LiveData<Student> studentLiveData = viewModel.getStudentLiveData();

        studentLiveData.observe(this, new Observer<Student>()
        {
            public void onChanged(@Nullable Student student)
            {
                    studentList = viewModel.getStudentList();
                    if (studentList != null) {
                        studentsViewAdapter = new StudentRecyclerAdapter(studentList);
                        studentsView.setAdapter(studentsViewAdapter);
                    }

            }
        });

    }


}
