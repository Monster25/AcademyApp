package com.application.academy.views;

import android.animation.Animator;
import android.content.res.Configuration;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;

import com.application.academy.views.fragments.AddStudentFragment;
import com.application.academy.views.fragments.AgendaFragment;
import com.application.academy.firebase.FirebaseAdapter;
import com.application.academy.R;
import com.application.academy.model.StudentList;
import com.application.academy.views.fragments.StudentsFragment;
import com.application.academy.viewmodel.StudentViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.Exclude;

import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNav;
    Toolbar topNav;
    private StudentViewModel viewModel;
    private String COMMON_TAG;
    private View fadeBackground;
    private FrameLayout mainFragmentLayout, addStudentFragmentLayout;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Driving Academy");

            viewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        mainFragmentLayout = findViewById(R.id.fragmentPlaceholder);
        addStudentFragmentLayout = findViewById(R.id.addStudentFragmentPlaceholder);
        fadeBackground = findViewById(R.id.fadeBackground);

        topNav =  findViewById(R.id.app_toolbar);
        setSupportActionBar(topNav);




        bottomNav = findViewById(R.id.bottom_navigation);

        //Bottom navigation listeners
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                displayView(item.getItemId());
                return true;
            }
        });



        //Default fragment
    displayView(R.id.menu_agenda);





    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.top_navigation_items,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Top bar item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Fragment fragment = null;
        switch(item.getItemId())
        {
            case R.id.action_settings:
                //User chooses the "Settings" item, show the app settings UI
                fragment = new AddStudentFragment();
                return true;

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

    //Bottom Nav Fragment Navigation
    public void displayView(int viewId) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        String tag = "";
        switch (viewId) {
            case R.id.menu_students:
                fragment = new StudentsFragment();
                title = "Students";
                tag = "students_tag";
                break;

            case R.id.menu_agenda:
                fragment = new AgendaFragment();
                title = "Agenda";
                tag = "agenda_tag";
                break;

            default:
                break;

        }

        //Switch to that fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentPlaceholder, fragment);
             ft.commit();
             //setTitle(title);
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
        public StudentViewModel getViewModel()
        {
            return viewModel;
        }

        //Fade background between the two fragments

        public void fadeBackground()
        {
            //fadeBackground.setVisibility(View.VISIBLE);
           // fadeBackground.setAlpha(0f);
            addStudentFragmentLayout.setClickable(true);
            bottomNav.setVisibility(GONE);
            fadeBackground.animate().alpha(0.5f); //grey out value

        }

        public void unFadeBackground()
        {
            //ANIMATION - NEED TESTING
            //fadeBackground.setAlpha(0.5f);
            addStudentFragmentLayout.setClickable(false);
            bottomNav.setVisibility(VISIBLE);
            fadeBackground.animate().alpha(0.0f);
        }


        //Make sure app doesn't close due to pressing back.
    @Override
    public void onBackPressed()
    {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.addStudentFragmentPlaceholder);
        if (fragment instanceof AddStudentFragment)
        {
            unFadeBackground();
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
