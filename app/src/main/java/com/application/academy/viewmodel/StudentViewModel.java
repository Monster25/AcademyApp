package com.application.academy.viewmodel;

import com.application.academy.firebase.FirebaseAdapter;
import com.application.academy.firebase.FirebaseQueryLiveData;
import com.application.academy.model.Session;
import com.application.academy.model.SessionList;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.application.academy.repository.StudentRepository;
import com.google.firebase.database.DataSnapshot;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class StudentViewModel extends ViewModel {

    private static final String studentRefString = "/Students";
    private static final String sessionRefString = "/Sessions";

    private final StudentRepository studentRepository = StudentRepository.getInstance();

//Student Live data
    private final FirebaseQueryLiveData studentLiveQuery = studentRepository.firebaseQueryLiveData(studentRefString);
//Session Live data
    private final FirebaseQueryLiveData sessionLiveQuery = studentRepository.firebaseQueryLiveData(sessionRefString);

    private final FirebaseAdapter adapter = FirebaseAdapter.getInstance();

    private final LiveData<Student> studentLiveData = Transformations.map(studentLiveQuery, new StudentDeserializer());

    private final LiveData<Session> sessionLiveData = Transformations.map(sessionLiveQuery, new SessionDeserializer());


    private class StudentDeserializer implements Function<DataSnapshot, Student>
    {
        @Override
        public Student apply(DataSnapshot dataSnapshot)
        {
            return dataSnapshot.getValue(Student.class);
        }
    }

    private class SessionDeserializer implements Function<DataSnapshot, Session>
    {
        @Override
        public Session apply(DataSnapshot dataSnapshot)
        {
            return dataSnapshot.getValue(Session.class);
        }
    }

    @NonNull
    public LiveData<Student> getStudentLiveData()
    {
        return studentLiveData;
    }

    @NonNull
    public LiveData<Session> getSessionLiveData() {return sessionLiveData;}

    @NonNull
    public SessionList getSessionList(String date) {
        return adapter.makeSessionList(sessionLiveQuery.getValue(), date);
    }

    @NonNull
    public boolean isSessionList()
    {
        if (sessionLiveQuery.getValue() == null)
        {
            return false;
        }
        else
            return true;
    }

    @NonNull
    public String getStudentName(int id)
    {
        return studentRepository.getStudentName(studentLiveQuery, id, studentRefString);
    }

    @NonNull
    public void addSession(int id, String time, String date, int studentId)
    {
        studentRepository.addSession(new Session(id, time, date, studentId), sessionRefString, sessionLiveQuery);
    }

    @NonNull
    public void removeSession(int id)
    {
        studentRepository.removeSession(sessionRefString, id, sessionLiveQuery);
    }

    @NonNull
    public void setSession(int id, String time, String date, int studentId)
    {
        studentRepository.setSession(new Session(id, time, date, studentId), sessionRefString, sessionLiveQuery);
    }

    @NonNull
    public StudentList getStudentList() {
        return adapter.makeStudentList(studentLiveQuery.getValue());
    }

    @NonNull
    public boolean isStudentList()
    {
        if (studentLiveQuery.getValue() == null)
        {
            return false;
        }
        else
            return true;
    }

    @NonNull
    public void addStudent(String studentName, boolean paid, int sessionNumber, int id)
    {
        studentRepository.addStudent(new Student(studentName, paid, sessionNumber, id), studentRefString, studentLiveQuery);
    }

    @NonNull
    public void removeStudent(int id)
    {
        studentRepository.removeStudent(studentRefString, id, studentLiveQuery);
    }

    @NonNull
    public void setStudent(String studentName, boolean paid, int sessionNumber, int id)
    {
        studentRepository.setStudent(new Student(studentName, paid, sessionNumber, id), studentRefString, studentLiveQuery);
    }

    @NonNull
    public void getTemp(String requestUrl) throws IOException, JSONException, InterruptedException, ExecutionException {
        studentRepository.getTemp(requestUrl);
    }



    @NonNull
    public MutableLiveData<String> getLiveResponseData()
    {
        return studentRepository.getLiveResponseData();
    }

}
