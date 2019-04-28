package com.application.academy.viewmodel;

import com.application.academy.firebase.FirebaseAdapter;
import com.application.academy.firebase.FirebaseQueryLiveData;
import com.application.academy.firebase.FirebaseSingleDataListener;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.application.academy.repository.Repository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class StudentViewModel extends ViewModel {

    private static final String refString = "/Students";
    private Student student;
    //private static final String childRefString = "Student";
   // private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference(refString);

    private final Repository repository = new Repository();

   // private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(ref);

    private final FirebaseQueryLiveData liveData = repository.firebaseQueryLiveData(refString);

    private final FirebaseAdapter adapter = FirebaseAdapter.getInstance();

    private final LiveData<Student> studentLiveData = Transformations.map(liveData, new Deserializer());


    private class Deserializer implements Function<DataSnapshot, Student>
    {
        @Override
        public Student apply(DataSnapshot dataSnapshot)
        {
            return dataSnapshot.getValue(Student.class);
        }
    }

    @NonNull
    public LiveData<Student> getStudentLiveData()
    {
        return studentLiveData;
    }

    @NonNull
    public StudentList getStudentList() {
        return adapter.makeStudentList(liveData.getValue());
    }

    @NonNull
    public boolean isStudentList()
    {
        if (liveData.getValue() == null)
        {
            return false;
        }
        else
            return true;
    }

    @NonNull
    public void addStudent(String studentName, boolean paid, int sessionNumber, int id)
    {
        repository.addStudent2(new Student(studentName, paid, sessionNumber, id), refString, liveData);
    }

    @NonNull
    public void removeStudent(int id)
    {
        repository.removeStudent(refString, id, liveData);
    }

    @NonNull
    public void setStudent(String studentName, boolean paid, int sessionNumber, int id)
    {
        repository.setStudent(new Student(studentName, paid, sessionNumber, id), refString, liveData);
    }

    @NonNull
    public void getTemp(String requestUrl) throws IOException, JSONException, InterruptedException, ExecutionException {
        repository.getTemp(requestUrl);
    }

}
