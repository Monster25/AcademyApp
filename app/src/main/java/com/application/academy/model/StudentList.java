package com.application.academy.model;

import com.application.academy.model.Student;

import java.util.ArrayList;

public class StudentList extends Student {

    private ArrayList<Student> students;

    public StudentList()
    {
        students = new ArrayList<Student>();
    }

    public Student getStudent(int i)
    {
        return students.get(i);
    }

    public Student getStudentById(int id) {
        Student student = null;
        for (int i=0; i<students.size();i++)
            if (students.get(i).getId() == id)
                student = students.get(id);
            return student;
    }

    public void addStudent(Student student)
    {
        students.add(student);
    }

    public void removeStudent(int i)
    {
        students.remove(i);
    }

    public void setStudent(Student student, int i) {students.set(i, student);}

    public void setStudentList(ArrayList<Student> students)
    {
        this.students = students;
    }

    public int size()
    {
        return students.size();
    }

    public Student getLastStudent() { return students.get(students.size()-1);}
}
