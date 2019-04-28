package com.application.academy.apiclient;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseError;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class WebAPIClient {

    private String jsonString;

    public String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null)
            return jsonResponse;
           // return liveResponseData;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000); // miliseconds
            urlConnection.setConnectTimeout(15000); //miliseconds
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
        //return liveResponseData;

    }

    public String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();

    }

    public class TempAsyncTask extends AsyncTask<String, String, String> implements ClientResponse{

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            String jsonResponse = "";

            try {
                url = new URL(strings[0]);
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s)
        {
            try {
                onResponseReceived(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public  void onResponseReceived(String result) throws JSONException {

        }

        @Override
        public void onFailed(DatabaseError databaseError)
        {

        }

    }

}
