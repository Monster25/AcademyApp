package com.application.academy.views.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.application.academy.R;
import com.application.academy.model.StudentList;
import com.application.academy.viewmodel.StudentViewModel;
import com.application.academy.views.fragments.AddSessionFragment;
import com.application.academy.views.fragments.AgendaFragment;
import com.application.academy.views.fragments.SessionsFragment;
import com.application.academy.views.fragments.StudentsFragment;

public class DateActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private StudentViewModel viewModel;
    private StudentList studentList;
    private String COMMON_TAG;
    private View fadeBackground;
    public static DateActivity instance;
    private FrameLayout  addSessionFragmentLayout, mainFragmentLayout;
    private int lastId;
    private String date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_activity);
        instance = this;
        fadeBackground = findViewById(R.id.fadeBackground);
        addSessionFragmentLayout = findViewById(R.id.fragmentPlaceholder);
        mainFragmentLayout = findViewById(R.id.sessionsFragmentPlaceholder);
        toolbar =  findViewById(R.id.toolbar);
       // toolbar = MainActivity.getInstance().getTopNav();

        //toolbar.setTitle("Sessions");


        Bundle bundle = getIntent().getExtras();

        date = bundle.getString("date");

        toolbar.setTitle(date);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        Log.d("Cacat", viewModel.toString());
        //Fragment fragment = new AddSessionFragment();


        //Default fragment
        displayView(0);
    }

    static public DateActivity getInstance()
    {
        return instance;
    }

    public String getDate()
    {
        return date;
    }

    public StudentViewModel getViewModel()
    {
        return viewModel;
    }

        @Override
        //Top bar options
        public boolean onCreateOptionsMenu(Menu menu)
        {
            getMenuInflater().inflate(R.menu.top_navigation_items_agenda,menu);
            return super.onCreateOptionsMenu(menu);
        }

        //top bar selections
        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            Fragment fragment = null;
            switch(item.getItemId())
            {
                case R.id.action_favorite:
                    //Add addStudent Fragment but check if it already exists.
                    fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentPlaceholder);
                    if (fragment instanceof AddSessionFragment);
                    else
                    {
                        fadeBackground();
                        fragment = new AddSessionFragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentPlaceholder, fragment);
                        ft.commit();
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

        }

    //Keep fragments from resetting due to orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Log.i(COMMON_TAG, "landscape");

        }
        else if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT)
        {
            Log.i(COMMON_TAG, "portrait");
        }
    }

    public void fadeBackground()
    {
        //fadeBackground.setVisibility(View.VISIBLE);
        // fadeBackground.setAlpha(0f);
        addSessionFragmentLayout.setClickable(true);
        fadeBackground.animate().alpha(0.5f); //grey out value

    }

    public void unFadeBackground()
    {
        //ANIMATION - NEED TESTING
        //fadeBackground.setAlpha(0.5f);
        addSessionFragmentLayout.setClickable(false);
        fadeBackground.animate().alpha(0.0f);
    }


    //Make sure app doesn't close due to pressing back.
    @Override
    public void onBackPressed()
    {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentPlaceholder);
        if (fragment instanceof AddSessionFragment)
        {
            unFadeBackground();
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        else
        {
            super.onBackPressed();
        }
    }

    //Main Fragment Navigation
    public void displayView(int viewId) {
        Fragment fragment = null;
        switch (viewId) {
            case 0:
                fragment = new SessionsFragment();
                break;

            default:
                break;

        }

        //Switch to that fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.sessionsFragmentPlaceholder, fragment);
            ft.commit();
            //setTitle(title);
        }
    }

}
