package org.example;

import org.example.DBObjects.*;
import org.hibernate.ObjectDeletedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.*;

public class ORMSession {

    //PRIVATE ATTRIBUTES
    private Session        session;
    private SessionFactory sessionFactory;
    private String         source;



    //PUBLIC INTERFACE
    public ORMSession(String source) {
        generateSession();
        setSource(source);
    }

    public void closeSession() {
        getSession().close();
    }

    public void controlAndSave(Driver driver) {
        //This check makes sure that no drivers with the same firstname, same lastname and same nationality exists in the database
        for (Driver d : getSession().createQuery("FROM Driver", Driver.class).list()) {
            if (d.getFirstnameDriver().equals(driver.getFirstnameDriver()) && d.getLastnameDriver().equals(driver.getLastnameDriver()) && d.getNationalityDriver().equals(driver.getNationalityDriver())) {
                return;
            }
        }
        getSession().save(driver); //If the loop ends, it means that no driver with the same firstname, lastname and nationality exists in the database
        beginAndCommitTransaction();

        logDatabaseSave(driver, driver.getFirstnameDriver() + driver.getLastnameDriver());
    }

    public void controlAndSave(Constructor constructor) {
        //This check makes sure that no constructors with the same name already exists in the database
        for (Constructor c : getSession().createQuery("FROM Constructor", Constructor.class).list()) {
            if (c.getNameConstructor().equals(constructor.getNameConstructor())) {
                return;
            }
        }
        getSession().save(constructor); //If the loop ends, it means that no constructor with the same name exists in the database
        beginAndCommitTransaction();

        logDatabaseSave(constructor, constructor.getNameConstructor());
    }

    public void controlAndSave(Race race) {
        //This check makes sure that no race with the same cityLocation already exists in the database
        for (Race c : getSession().createQuery("FROM Race", Race.class).list()) {
            if (c.getCityLocationRace().equals(race.getCityLocationRace())) {
                return;
            }
        }
        getSession().save(race); //If the loop ends, it means that no race with the same cityLocation exists in the database
        beginAndCommitTransaction();

        logDatabaseSave(race, race.getCityLocationRace());
    }



    //PRIVATE INTERFACE
    private void beginAndCommitTransaction() {
        Transaction transaction = getSession().beginTransaction();
        transaction.commit();
    }

    private void generateSession() {
        Configuration config = new Configuration();

        //ADD NEW CLASSES HERE
        config.addClass(Driver.class);
        config.addClass(Constructor.class);
        config.addClass(Race.class);
        config.addClass(Teamed.class);
        config.addClass(Earned.class);

        setSessionFactory(config.buildSessionFactory());
        setSession(sessionFactory.openSession());
    }

    private void logDatabaseSave(Object object, String identifier) {
        //Check if file exists before creating it if not or oppening it if it does
        File file = new File(DATABASE_SAVE_LOG_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Writer output;
            output = new BufferedWriter(new FileWriter(file, true));
            output.append(Object.class.toString() + " added from " + getSource() + ". Identifier : " + identifier + "\n");
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Session getSession() {
        return session;
    }
    private void setSession(Session session) {
        this.session = session;
    }

    private String getSource() {
        return source;
    }
    private void setSource(String source) {
        this.source = source;
    }

    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    //PRIVATE CONSTANTS
    private static final String DATABASE_SAVE_LOG_FILE_PATH = "databaseSaveLog.txt";

}
