package org.example;

import org.example.DBObjects.Constructor;
import org.example.DBObjects.Driver;
import org.example.DBObjects.Race;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ORMSession {

    private Session session;
    private SessionFactory sessionFactory;

    public ORMSession() {
        generateSession();
    }

    public Session getSession() {
        return session;
    }

    public void closeSession() {
        session.close();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void closeSessionFactory() {
        sessionFactory.close();
    }

    private void generateSession() {
        Configuration config = new Configuration();

        //ADD NEW CLASSES HERE
        config.addClass(Driver.class);
        config.addClass(Constructor.class);
        config.addClass(Race.class);

        sessionFactory = config.buildSessionFactory();
        session = sessionFactory.openSession();
    }

}