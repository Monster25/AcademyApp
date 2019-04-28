package com.application.academy.model;

public class Session {

    private int id;
    private String time;
    private String date;
    private int studentId;

    public Session()
    {

    }

    public Session (int id, String time, String date, int studentId)
    {
        this.id = id;
        this.time = time;
        this.date = date;
        this.studentId = studentId;

    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getTime()
    {
        return time;
    }

    public String getDate()
    {
        return date;
    }
}
