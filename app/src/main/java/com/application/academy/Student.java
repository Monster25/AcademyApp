package com.application.academy;

import com.google.firebase.database.Exclude;

import java.util.concurrent.atomic.AtomicInteger;

public class Student {
    //Exclude actual incrementer from database
    @Exclude
    private static final AtomicInteger incrementer = new AtomicInteger(-1); //id incrementer

    private int id;
    private String name;
    private boolean payed;
    private int sessions;

    public Student()
    {

    }

    public Student(String name, boolean payed, int sessions)
    {
        this.name = name;
        this.payed = payed;
        this.sessions = sessions;
        id = incrementer.incrementAndGet(); //increment id
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public boolean getPayed()
    {
        return payed;
    }

    public int getSessions()
    {
        return sessions;
    }

}
