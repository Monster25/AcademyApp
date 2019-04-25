package com.application.academy.views.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.academy.R;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.application.academy.views.MainActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> {

    private ArrayList<Student> students;
    private StudentList studentList;
    private Fragment fragment;

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.student_view, parent, false);


        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        viewHolder.name.setText(studentList.getStudent(position).getName());
       // viewHolder.icon.setImageResource(viewHolder.getIcon());
    }

    public int getItemCount()
    {
        return studentList.size();
    }
    //Provide a reference to the views for each data item
    //Complex data items may need more than one view per item, and
    //you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.student_name);

        }
    }

    public StudentRecyclerAdapter(StudentList studentList)
    {
        this.studentList = studentList;
    }
}