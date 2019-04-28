package com.application.academy.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.academy.R;
import com.application.academy.model.Session;
import com.application.academy.model.SessionList;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.application.academy.viewmodel.StudentViewModel;
import com.application.academy.views.activities.DateActivity;
import com.application.academy.views.activities.MainActivity;

public class SessionsFragment extends Fragment {

    RecyclerView sessionsView;
    RecyclerView.Adapter sessionsViewAdapter;

    public static final String KEY_ITEM = "unique_key";
    public static final String KEY_INDEX = "index_key";
    private String mTime;

    private StudentViewModel viewModel;
    private int lastId = -1;
    private SessionList sessionList;
    private StudentList studentList;
    private Button button;
    private TextView noSession;
    private RecyclerView.LayoutManager sessionsViewLayout;
    private SessionsFragment instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.sessions_fragment, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        instance = this;
        noSession = view.findViewById(R.id.noSession);

        sessionsView = view.findViewById(R.id.rv);

        sessionsView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        sessionsView.setLayoutManager(manager);



        manager.setStackFromEnd(true);

        viewModel = ((DateActivity) getActivity()).getViewModel();

//get session list
        LiveData<Session> sessionLiveData = viewModel.getSessionLiveData();

        sessionLiveData.observe(this, new Observer<Session>()
        {
            public void onChanged(@Nullable Session session)
            {
                sessionList = viewModel.getSessionList(DateActivity.getInstance().getDate());
                if (sessionList != null) {

                    //get student list
                    LiveData<Student> studentLiveData = viewModel.getStudentLiveData();

                    studentLiveData.observe(instance, new Observer<Student>()
                    {
                        public void onChanged(@Nullable Student student)
                        {
                            studentList = viewModel.getStudentList();
                            if (studentList!=null) {
                                sessionsViewAdapter = new SessionRecyclerAdapter(sessionList, studentList);
                                sessionsView.setAdapter(sessionsViewAdapter);

                                //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDelete((SessionRecyclerAdapter)sessionsViewAdapter));
                                //itemTouchHelper.attachToRecyclerView(sessionsView);
                            }
                        }
                    });

                }

                if (sessionList.size() == 0 || sessionList == null)
                    noSession.setVisibility(View.VISIBLE);
                else
                {
                    noSession.setVisibility(View.GONE);
                }
            }
        });


    }
}
