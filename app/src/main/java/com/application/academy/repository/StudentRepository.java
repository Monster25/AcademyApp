package com.application.academy.repository;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.application.academy.apiclient.APIAdapter;
import com.application.academy.apiclient.WebAPIClient;
import com.application.academy.firebase.FirebaseQueryLiveData;
import com.application.academy.firebase.FirebaseSingleDataListener;
import com.application.academy.model.Session;
import com.application.academy.model.Student;
import com.application.academy.views.activities.DateActivity;
import com.application.academy.views.activities.MainActivity;
import com.application.academy.views.fragments.AgendaFragment;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class StudentRepository {

    private static final APIAdapter adapter = new APIAdapter();
    private static final WebAPIClient client = new WebAPIClient();
    private MutableLiveData<String> liveResponseData = new MutableLiveData<String>();
    private WebAPIClient.TempAsyncTask task;
    private String jsonString;
    private String studentName;
    private static StudentRepository instance = new StudentRepository();
    private StudentRepository()
    {
        liveResponseData.setValue("999");
    }

    public static StudentRepository getInstance()
    {
        return instance;
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
    public String getStudentName(FirebaseQueryLiveData liveData, int studentId, String refString)
    {
        final String cRefString = refString;
        studentName = "";
        liveData.getChildKeyByValue("id", studentId, new FirebaseSingleDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String childKey, String childName) {
                studentName = childName;
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
        return studentName;
    }



    @NonNull
    public void addSession (Session session, String refString, FirebaseQueryLiveData liveData)
    {
        final String cRefString = refString;
        final Session cSession = session;

        computeFirebaseReference(cRefString).push().setValue(cSession);

        //Use interface to asynchonously check if the student has been added.
        liveData.getChildKeyByValue("id", cSession.getId(), new FirebaseSingleDataListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(String childKey, String childName) {

                //Confirm toast
                Toast toast = Toast.makeText(DateActivity.getInstance(), "Session added!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

                //Confirm toast
                Toast toast = Toast.makeText(DateActivity.getInstance(), "Session adding failed!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }
        });
    }

    @NonNull
    public void removeSession (String refString, int id, FirebaseQueryLiveData liveData)
    {
        final String cRefString = refString;

        //Use interface to confirm student removal asynchonously
        liveData.getChildKeyByValue("id", id, new FirebaseSingleDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String childKey, String childValue) {
                computeFirebaseReference(cRefString).child(childKey).removeValue();

                //Successful toast display:
                Toast toast = Toast.makeText(DateActivity.getInstance(), "Session removed!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                //Unsuccessful toast display:
                Toast toast = Toast.makeText(DateActivity.getInstance(), "Session removal unsuccessful!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }
        });
    }

    @NonNull
    public void setSession (Session session, String refString, FirebaseQueryLiveData liveData)
    {
        final String cRefString = refString;
        final Session cSession = session;
        //Use interface to asynchonously get the child key for a certain value.
        liveData.getChildKeyByValue("id", session.getId(), new FirebaseSingleDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String childKey, String childValue) {
                computeFirebaseReference(cRefString).child(childKey).setValue(cSession);


                //Confirm toast
                Toast toast = Toast.makeText(DateActivity.getInstance(), "Session updated!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {


                //Confirm toast
                Toast toast = Toast.makeText(DateActivity.getInstance(), "Session update failed!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL| Gravity.CENTER_VERTICAL, 0, -450);
                toast.show();
            }
        });
    }


    @NonNull
    public void addStudent (Student student, String refString, FirebaseQueryLiveData liveData)
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
            public void onSuccess(String childKey, String childValue) {

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
            public void onSuccess(String childKey, String childValue) {
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
            public void onSuccess(String childKey, String childValue) {
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
            liveResponseData.postValue(adapter.getTemp(result));
            //AgendaFragment.getInstance().refreshTemperature(adapter.getTemp(result));
            //return adapter.getTemp(task.execute(requestUrl).toString());
        }
        @Override
        public void onFailed(DatabaseError databaseError){
           // AgendaFragment.getInstance().refreshTemperature(null);
            Log.d("DatabaseError", databaseError.toString());
        }
    };

    task.execute(requestUrl);
    //return adapter.getTemp(jsonString);
    //return adapter.getTemp(task.execute(requestUrl).get());
}
//return the live data
public MutableLiveData<String> getLiveResponseData()
{
    return liveResponseData;
}

}
