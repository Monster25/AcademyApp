package com.application.academy;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class StudentViewModel extends ViewModel {
    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Students/Student0");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(ref);

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



}
