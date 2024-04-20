package org.example;

import org.example.DBObjects.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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
        //I added SQL where clause to make the query and the following loop way more efficient.
        Query query = getSession().createQuery("FROM Driver WHERE firstnamedriver = :firstnamedriver AND lastnamedriver = :lastnamedriver AND nationalitydriver = :nationalitydriver", Driver.class);
        query.setParameter("firstnamedriver", driver.getFirstnameDriver());
        query.setParameter("lastnamedriver", driver.getLastnameDriver());
        query.setParameter("nationalitydriver", driver.getNationalityDriver());

        for(int i = 0; i < query.list().size(); i++)
        {
            Driver d = (Driver) query.list().get(i);
            if (d.getFirstnameDriver().equals(driver.getFirstnameDriver()) && d.getLastnameDriver().equals(driver.getLastnameDriver()) && d.getNationalityDriver().equals(driver.getNationalityDriver())) {
                return;
            }
        }

        getSession().save(driver); //If the loop ends, it means that no driver with the same firstname, lastname and nationality exists in the database
        beginAndCommitTransaction();

        logDatabaseSave(driver, driver.getLastnameDriver() + " " + driver.getFirstnameDriver());
    }

    public void controlAndSave(Teamed teamed) {
        //This check makes sure that no teamed with the same idDriver, same idConstructor and same yearTeamed exists in the database

        Query<Teamed> query = getSession().createQuery("FROM Teamed WHERE idDriver = :idDriver AND idConstructor = :idConstructor AND yearteamed = :yearteamed", Teamed.class);
        query.setParameter("idDriver", teamed.getIdDriver());
        query.setParameter("idConstructor", teamed.getIdConstructor());
        query.setParameter("yearteamed", teamed.getYearTeamed());

        for (int i = 0; i < query.list().size(); i++) {
            Teamed t = (Teamed) query.list().get(i);
            if (t.getIdDriver() == teamed.getIdDriver()
                    && t.getIdConstructor() == teamed.getIdConstructor()
                    && t.getYearTeamed().equals(teamed.getYearTeamed())) {
                return;
            }
        }

        getSession().save(teamed); //If the loop ends, it means that no teamed with the same idDriver, idConstructor and yearTeamed exists in the database
        beginAndCommitTransaction();
    }

    public int getIdConstructor(String nameConstructor) {
        Query query = getSession().createQuery("FROM Constructor WHERE nameconstructor = :nameconstructor", Constructor.class);
        query.setParameter("nameconstructor", nameConstructor);

        try {
            Constructor constructor = (Constructor) query.list().get(0);
            return constructor.getIdConstructor();
        } catch (Exception e) {
            return -1;
        }
    }

    public int getIdDriver(String firstName, String lastName) {
        Query query = getSession().createQuery("FROM Driver WHERE firstnamedriver = :firstnamedriver AND lastnamedriver = :lastnamedriver", Driver.class);
        query.setParameter("firstnamedriver", firstName);
        query.setParameter("lastnamedriver", lastName);

        try {
            Driver driver = (Driver) query.list().get(0);
            return driver.getIdDriver();
        } catch (Exception e) {
            return -1;
        }
    }

    public void controlAndSave(Constructor constructor) {
        //This check makes sure that no constructors with the same name already exists in the database
        //I added SQL where clause to make the query and the following loop way more efficient.

        Query query = getSession().createQuery("FROM Constructor WHERE nameconstructor = :nameconstructor", Constructor.class);
        query.setParameter("nameconstructor", constructor.getNameConstructor());

        for(int i = 0; i < query.list().size(); i++)
        {
            Constructor c = (Constructor) query.list().get(i);
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
        //I added SQL where clause to make the query and the following loop way more efficient.

        Query query = getSession().createQuery("FROM Race WHERE citylocationrace = :citylocationrace", Race.class);
        query.setParameter("citylocationrace", race.getCityLocationRace());

        for(int i = 0; i < query.list().size(); i++)
        {
            Race r = (Race) query.list().get(i);
            if (r.getCityLocationRace().equals(race.getCityLocationRace())) {
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

        //That way, the logs txt files will have one line per line added in database.
        //Note that if you reset the database, you should also reset the log txt file.

        try {
            Writer output;
            output = new BufferedWriter(new FileWriter(file, true));
            output.append(object.getClass().toString() + " added from " + getSource() + ". Identifier : " + identifier + "\n");
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
