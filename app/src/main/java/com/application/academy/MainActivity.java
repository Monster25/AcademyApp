package com.application.academy;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNav;

    //Exclude actual incrementer from database
    @Exclude
    public final static AtomicInteger incrementer = new AtomicInteger(-1); //id incrementer

    private StudentList studentList;
    private int lastId = -1;
    private StudentList students;
    private FirebaseAdapter adapter;
    private StudentViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
    displayView(R.id.menu_agenda);





    }

    public void displayView(int viewId)
    {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId)
        {
            case R.id.menu_students:
                fragment = new StudentsFragment();
                title = "Students";
                break;

            case R.id.menu_agenda:
                fragment = new AgendaFragment();
                title = "Agenda";
                break;

            default:
                break;

        }

        if (fragment!=null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentPlaceholder, fragment);
            ft.commit();
        }

    }




}
