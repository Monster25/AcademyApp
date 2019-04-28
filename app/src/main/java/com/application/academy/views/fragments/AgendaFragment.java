package com.application.academy.views.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.application.academy.R;
import com.application.academy.viewmodel.StudentViewModel;
import com.application.academy.views.activities.DateActivity;
import com.application.academy.views.activities.MainActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AgendaFragment extends Fragment {

    StudentViewModel viewModel;
    String temperature;
    TextView tempDisplay;
    CalendarView calendarView;
    public static AgendaFragment instance;

    public static final String KEY_ITEM = "unique_key";
    public static final String KEY_INDEX = "index_key";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.agenda_fragment, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //Quick connection no Async task for quick testing
    //    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

      //  StrictMode.setThreadPolicy(policy);
        instance = this;
        viewModel = ((MainActivity) getActivity()).getViewModel();
        //Interactable UI assignments
        tempDisplay = view.findViewById(R.id.temp);
        calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            String date = dayOfMonth + "/" + month + "/" + year;
            Log.d("CalendarActivity", "onSelectedDayChange: dd/mm/yyyy" + date);
            Intent intent = new Intent(MainActivity.getInstance(),DateActivity.class);
            intent.putExtra("date", date);
            startActivity(intent);
            }
        });

        //Get current temperature in Iasi Romania every time the fragment is created
        //API call
        startRetrievingTemperature();

        tempDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRetrievingTemperature();
            }
        });

    }

    public void saveToSharedPreferences(String key, String value)
    {
        //Get SharedPreference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //Get editor
        SharedPreferences.Editor editor = prefs.edit();
        //Store value
        editor.putString(key,temperature);
        //Apply changes
        editor.apply();
    }

    public String getFromSharedPreferences(String key, String defaultValue)
    {
        //use stored value
        //Get SharedPreference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //Retrieve value
        String savedTemp = prefs.getString(key, defaultValue);
        //Set value
        return savedTemp;
    }

    public String kelvinToCelsius(String kelvin)
    {
        double kelvinValue = 273.15;
        return String.valueOf((int)(Double.parseDouble(kelvin)-kelvinValue));
    }

    public void startRetrievingTemperature()
    {
        try {
            //Iasi id = 675810
            //Api key = 296d876d62ecdf7b528152a666071179
            viewModel.getTemp("https://api.openweathermap.org/data/2.5/weather?id=675810&appid=296d876d62ecdf7b528152a666071179");
        } catch (InterruptedException e) {
            //Last cached value
            e.printStackTrace();
        } catch (ExecutionException e) {
            //Last cahced value
            e.printStackTrace();
        }
        catch (IOException | JSONException ex){
            //Last cached valued
            ex.printStackTrace();
        }
    }

    public void refreshTemperature(String result)
    {
        temperature = result;
        if (temperature != null) {
            tempDisplay.setText("Temperature: "+kelvinToCelsius(temperature)+" C");

            saveToSharedPreferences("temp", temperature);
        }
        else {
            tempDisplay.setText("Temperature: "+kelvinToCelsius(getFromSharedPreferences("temp", "999"))+" C");
        }
    }

    public static AgendaFragment getInstance()
    {
        return instance;
    }
}
