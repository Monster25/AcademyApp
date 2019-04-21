package com.application.academy.views;

import android.content.res.Configuration;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

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


public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNav;
    Toolbar topNav;
    private String COMMON_TAG;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                //User chooses the "Settings" item, show the app settings UI
                return true;

            case R.id.action_favorite:
                //User chooses the "Favorite" action, mark the current item
                //as favorite
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

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


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentPlaceholder, fragment);
             ft.commit();
             setTitle(title);
        }
    }



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


}
