package com.application.academy.viewmodel;

import android.util.Log;

import com.application.academy.firebase.FirebaseAdapter;
import com.application.academy.firebase.FirebaseQueryLiveData;
import com.application.academy.firebase.OnGetDataListener;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class StudentViewModel extends ViewModel {

    private static final String refString = "Students";
    private Student student;
    private static final String childRefString = "Student";
    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/"+refString);

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(ref);

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
        Student student = new Student(studentName, paid, sessionNumber, id);
        ref.push().setValue(student);
    }

    @NonNull
    public void removeStudent(int id)
    {
        liveData.getChildKeyByValue("id", id, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String childKey) {
                ref.child(childKey).removeValue();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    public void setStudent(String studentName, boolean paid, int sessionNumber, int id)
    {
        student = new Student(studentName, paid, sessionNumber, id);
        //Use interface to asynchonously get the child key for a certain value.
        liveData.getChildKeyByValue("id", id, new OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String childKey) {
                ref.child(childKey).setValue(student);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

    }

}
