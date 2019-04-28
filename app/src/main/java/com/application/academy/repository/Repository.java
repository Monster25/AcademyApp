package com.application.academy.repository;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.application.academy.apiclient.APIAdapter;
import com.application.academy.apiclient.WebAPIClient;
import com.application.academy.firebase.FirebaseQueryLiveData;
import com.application.academy.firebase.FirebaseSingleDataListener;
import com.application.academy.model.Student;
import com.application.academy.views.activities.MainActivity;
import com.application.academy.views.fragments.AgendaFragment;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Repository  {

    private static final APIAdapter adapter = new APIAdapter();
    private static final WebAPIClient client = new WebAPIClient();
    private WebAPIClient.TempAsyncTask task;
    private String jsonString;
    public Repository ()
    {

    }
@NonNull
    public DatabaseReference computeFirebaseReference(String ref)
    {
        return FirebaseDatabase.getInstance().getReference(ref);
    }
@NonNull
    public FirebaseQueryLiveData firebaseQueryLiveData (String ref)
    {
        return new FirebaseQueryLiveData(computeFirebaseReference(ref));
    }
@NonNull
    public void addStudent (Student student, String ref)
    {
        computeFirebaseReference(ref).push().setValue(student);
    }

    @NonNull
    public void addStudent2 (Student student, String refString, FirebaseQueryLiveData liveData)
    {
        final String cRefString = refString;
        final Student cStudent = student;

        computeFirebaseReference(cRefString).push().setValue(cStudent);
        //Use interface to asynchonously check if the student has been added.
        liveData.getChildKeyByValue("id", cStudent.getId(), new FirebaseSingleDataListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(String childKey) {

                //Confirm toast
                Toast toast = Toast.makeText(MainActivity.getInstance(), "Student added!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

                //Confirm toast
                Toast toast = Toast.makeText(MainActivity.getInstance(), "Student adding failed!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }
        });
    }

    @NonNull
    public void removeStudent (String refString, int id, FirebaseQueryLiveData liveData)
    {
        final String cRefString = refString;

        //Use interface to confirm student removal asynchonously
        liveData.getChildKeyByValue("id", id, new FirebaseSingleDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String childKey) {
                computeFirebaseReference(cRefString).child(childKey).removeValue();

                //Successful toast display:
                Toast toast = Toast.makeText(MainActivity.getInstance(), "Student removed!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                //Unsuccessful toast display:
                Toast toast = Toast.makeText(MainActivity.getInstance(), "Student removing failed.!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }
        });
    }

    @NonNull
    public void setStudent (Student student, String refString, FirebaseQueryLiveData liveData)
    {
        final String cRefString = refString;
        final Student cStudent = student;
        //Use interface to asynchonously get the child key for a certain value.
        liveData.getChildKeyByValue("id", student.getId(), new FirebaseSingleDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String childKey) {
                computeFirebaseReference(cRefString).child(childKey).setValue(cStudent);


                //Confirm toast
                Toast toast = Toast.makeText(MainActivity.getInstance(), "Student updated!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {


                //Confirm toast
                Toast toast = Toast.makeText(MainActivity.getInstance(), "Student update failed!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }
        });
    }

    //Implemented interface to be able to get jsonresponse
@NonNull
    public void getTemp (final String requestUrl) throws JSONException, InterruptedException {
        //GENIUS, CALL SEPARATE METHOD ON CALLING FRAGMENT, defeats MVVM purpose but I couldnt' find any other way
    //than directly modifying UI elements.
    task = client.new TempAsyncTask() {
        @Override
        public void onResponseReceived(String result) throws JSONException {
            AgendaFragment.getInstance().refreshTemperature(adapter.getTemp(result));
            //return adapter.getTemp(task.execute(requestUrl).toString());
        }
        @Override
        public void onFailed(DatabaseError databaseError){
            Log.d("DatabaseError", databaseError.toString());
        }
    };

    task.execute(requestUrl);
    //return adapter.getTemp(jsonString);
    //return adapter.getTemp(task.execute(requestUrl).get());
}
}
