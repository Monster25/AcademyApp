package com.application.academy.model;

import java.util.ArrayList;

public class SessionList extends Session {

    private ArrayList<Session> sessions;

    public SessionList()
    {
        sessions = new ArrayList<Session>();
    }

    public Session getSession(int i)
    {
        return sessions.get(i);
    }

    public void addSession(Session session)
    {
        sessions.add(session);
    }

    public void removeSession(int i)
    {
        sessions.remove(i);
    }

    public void setSession(Session session, int i) {sessions.set(i, session);}


    public void setSessionList(ArrayList<Session> sessions)
    {
        this.sessions = sessions;
    }

    public int size()
    {
        return sessions.size();
    }

    public Session getLastSession() { return sessions.get(sessions.size()-1);}
}
