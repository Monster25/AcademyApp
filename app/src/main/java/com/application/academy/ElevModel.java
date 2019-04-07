package com.application.academy;

public class ElevModel {
    private int id;
    private String name;
    private boolean payed;
    private int sessions;

    public ElevModel(String name, boolean payed, int sessions)
    {
        this.name = name;
        this.payed = payed;
        this.sessions = sessions;
    }

    public void setId(int id)
    {
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

    public boolean getPayed()
    {
        return payed;
    }

    public int getSessions()
    {
        return sessions;
    }


}
