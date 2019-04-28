package com.application.academy.firebase;

import com.application.academy.model.Session;
import com.application.academy.model.SessionList;
import com.application.academy.model.Student;
import com.application.academy.model.StudentList;
import com.google.firebase.database.DataSnapshot;

public class FirebaseAdapter {

    private static final FirebaseAdapter adapter = new FirebaseAdapter();
    private FirebaseAdapter()
    {

    }

    public static FirebaseAdapter getInstance()
    {
        return adapter;
    }

    public StudentList makeStudentList(DataSnapshot dataSnapshot)
    {
        StudentList studentList = new StudentList();
        if (dataSnapshot != null) {

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Student student = new Student(snapshot.getValue(Student.class).getName(),
                        snapshot.getValue(Student.class).getPaid(),
                        snapshot.getValue(Student.class).getSessions(),
                        snapshot.getValue(Student.class).getId());
                studentList.addStudent(student);
            }
            return studentList;
        }
        else
            return studentList = null;
    }

    public SessionList makeSessionList(DataSnapshot dataSnapshot, String date)
    {
        SessionList sessionList = new SessionList();
        if (dataSnapshot != null)
        {
            for (DataSnapshot snapshot : dataSnapshot.getChildren())
            {
                if (snapshot.getValue(Session.class).getDate().matches(date)) {
                    Session session = new Session(snapshot.getValue(Session.class)
                            .getId(),
                            snapshot.getValue(Session.class).getTime(),
                            snapshot.getValue(Session.class).getDate(),
                            snapshot.getValue(Session.class).getStudentId());
                    sessionList.addSession(session);
                }
            }
            return sessionList;
        }
        else
            return sessionList = null;
    }

}
