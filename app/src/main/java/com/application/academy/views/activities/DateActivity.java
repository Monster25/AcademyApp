package com.application.academy.views.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.application.academy.R;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.application.academy.viewmodel.StudentViewModel;
import com.application.academy.views.fragments.AddStudentFragment;

public class DateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private StudentViewModel viewModel;
    private StudentList studentList;
    private int lastId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Sessions");

        Bundle bundle = getIntent().getExtras();

        String date = bundle.getString("date");

        toolbar.setTitle(date);

        viewModel = MainActivity.getInstance().getViewModel();

        //Top bar options
        public boolean onCreateOptionsMenu(Menu menu)
        {
            getMenuInflater().inflate(R.menu.top_navigation_items_agenda,menu);
            return super.onCreateOptionsMenu(menu);
        }
        //Top bar item selection
        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            Fragment fragment = null;
            switch(item.getItemId())
            {
                case R.id.action_favorite:
                    //Add addStudent Fragment but check if it already exists.
                    fragment = getSupportFragmentManager().findFragmentById(R.id.addStudentFragmentPlaceholder);
                    if (fragment instanceof AddStudentFragment);
                    else
                    {
                        fadeBackground();
                        fragment = new AddStudentFragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.addStudentFragmentPlaceholder, fragment);
                        ft.commit();
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

        }
    }
}
