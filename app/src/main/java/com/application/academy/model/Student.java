package com.application.academy.model;

import com.google.firebase.database.Exclude;

import java.util.concurrent.atomic.AtomicInteger;

public class Student {


    private int id;
    private String name;
    private boolean paid;
    private int sessions;

    public Student()
    {

    }

    public Student(String name, boolean paid, int sessions, int id)
    {
        this.name = name;
        this.paid = paid;
        this.sessions = sessions;
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public boolean getPaid()
    {
        return paid;
    }

    public int getSessions()
    {
        return sessions;
    }

}
