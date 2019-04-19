package com.application.academy.firebase;

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
        for(DataSnapshot snapshot : dataSnapshot.getChildren())
        {
            Student student = new Student(snapshot.getValue(Student.class).getName(),
                    snapshot.getValue(Student.class).getPaid(),
                    snapshot.getValue(Student.class).getSessions(),
                    snapshot.getValue(Student.class).getId());
            studentList.addStudent(student);
        }
        return studentList;
    }

}
