package com.application.academy.views.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.academy.R;
import com.application.academy.model.Session;
import com.application.academy.model.SessionList;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.application.academy.views.activities.DateActivity;

import java.util.ArrayList;
import java.util.Map;

public class SessionRecyclerAdapter extends RecyclerView.Adapter<SessionRecyclerAdapter.ViewHolder> {

    private ArrayList<Session> sessions;
    private SessionList sessionList;
    private StudentList studentList;
    private Map<String, Integer> sessionMap;
    private Fragment fragment;

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.session_view, parent, false);


        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
       // String name = DateActivity.getInstance().getViewModel().getStudentName(sessionList.getSession(position).getStudentId());
        try {
            viewHolder.name.setText(studentList.getStudentById(sessionList.getSession(position).getStudentId()).getName());
        } catch (NullPointerException e)
        {
            viewHolder.name.setText("Unknown Student.");
        }
      // Log.d("Cacat", studentList.getStudentById(0).getName());
        //viewHolder.name.setText(String.valueOf(sessionList.getSession(position).getStudentId()));
        viewHolder.time.setText(sessionList.getSession(position).getTime());
       // viewHolder.icon.setImageResource(viewHolder.getIcon());
    }

    public int getItemCount()
    {
        return sessionList.size();
    }
    //Provide a reference to the views for each data item
    //Complex data items may need more than one view per item, and
    //you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView time;
        ViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
        }
    }

    public SessionRecyclerAdapter(SessionList sessionList, StudentList studentList)
    {
        this.sessionList = sessionList;
        this.studentList = studentList;
    }
    //Add delete with swipe

}