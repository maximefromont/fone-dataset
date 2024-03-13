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

    public Driver getDriverFromLastnameFirstnameNationality(String lastname, String firstname, String nationality)
    {
        //I added SQL where clause to make the query and the following loop way more efficient.
        Query query = getSession().createQuery("FROM Driver WHERE lastnamedriver = :lastnamedriver AND firstnamedriver = :firstnamedriver AND nationalitydriver = :nationalitydriver", Driver.class);
        query.setParameter("lastnamedriver", lastname);
        query.setParameter("firstnamedriver", firstname);
        query.setParameter("nationalitydriver", nationality);

        //There should be only one result or nothing
        if(query.list().size() > 0)
        {
            return (Driver) query.list().get(0);
        }
        else
        {
            return null;
        }
    }

    public Driver getDriverFromId(int idDriver)
    {
        Query query = getSession().createQuery("FROM Driver WHERE iddriver = :iddriver", Driver.class);
        query.setParameter("iddriver", idDriver);

        //There should be only one result or nothing
        if(query.list().size() > 0)
        {
            return (Driver) query.list().get(0);
        }
        else
        {
            return null;
        }
    }

    public Constructor getConstructorFromNameConstructor(String nameConstructor)
    {
        //I added SQL where clause to make the query and the following loop way more efficient.
        Query query = getSession().createQuery("FROM Constructor WHERE nameconstructor = :nameconstructor", Constructor.class);
        query.setParameter("nameconstructor", nameConstructor);

        //There should be only one result or nothing
        if(query.list().size() > 0)
        {
            return (Constructor) query.list().get(0);
        }
        else
        {
            return null;
        }
    }

    public Constructor getConstructorFromId(int idConstructor)
    {
        Query query = getSession().createQuery("FROM Constructor WHERE idconstructor = :idconstructor", Constructor.class);
        query.setParameter("idconstructor", idConstructor);

        //There should be only one result or nothing
        if(query.list().size() > 0)
        {
            return (Constructor) query.list().get(0);
        }
        else
        {
            return null;
        }
    }

    public Race getRaceFromCityLocationRace(String cityLocationRace)
    {
        //I added SQL where clause to make the query and the following loop way more efficient.
        Query query = getSession().createQuery("FROM Race WHERE citylocationrace = :citylocationrace", Race.class);
        query.setParameter("citylocationrace", cityLocationRace);

        //There should be only one result or nothing
        if(query.list().size() > 0)
        {
            return (Race) query.list().get(0);
        }
        else
        {
            return null;
        }
    }

    public Teamed getTeamFromIdDriverIdConstructorYearTeamed(Driver driver, Constructor constructor, String yearTeamed)
    {
        Query query = getSession().createQuery("FROM Teamed WHERE iddriver = :iddriver AND idconstructor = :idconstructor AND yearteamed = :yearteamed", Teamed.class);
        query.setParameter("iddriver", driver);
        query.setParameter("idconstructor", constructor);
        query.setParameter("yearteamed", yearTeamed);

        //There should be only one result or nothing
        if (query.list().size() > 0)
        {
            return (Teamed) query.list().get(0);
        }
        else
        {
            return null;
        }
    }


    public void controlAndSave(Driver driver) {
        //This check makes sure that no drivers with the same firstname, same lastname and same nationality exists in the database
        Driver driverToCheck = getDriverFromLastnameFirstnameNationality(driver.getLastnameDriver(), driver.getFirstnameDriver(), driver.getNationalityDriver());

        if(driverToCheck == null) { //If getDriverFromLastnameFirstnameNationality(...) sends back null, it means this driver doesn't already exists
            getSession().save(driver); //If the loop ends, it means that no driver with the same firstname, lastname and nationality exists in the database
            beginAndCommitTransaction();

            logDatabaseSave(driver, driver.getFirstnameDriver() + " " + driver.getLastnameDriver());
        }
    }

    public void controlAndSave(Constructor constructor) {
        //This check makes sure that no constructors with the same name already exists in the database
        Constructor constructorToCheck = getConstructorFromNameConstructor(constructor.getNameConstructor());

        if (constructorToCheck == null) //If getConstructorFromNameconstructor(...) sends back null, it means this constructor doesn't already exists
        {
            getSession().save(constructor); //If the loop ends, it means that no constructor with the same name exists in the database
            beginAndCommitTransaction();

            logDatabaseSave(constructor, constructor.getNameConstructor());
        }
    }

    public void controlAndSave(Race race) {
        //This check makes sure that no race with the same cityLocation already exists in the database
        Race raceToCheck = getRaceFromCityLocationRace(race.getCityLocationRace());

        if (raceToCheck == null)  //If getRaceFromCityLocationRace(...) sends back null, it means this race doesn't already exists
        {
            getSession().save(race); //If the loop ends, it means that no race with the same cityLocation exists in the database
            beginAndCommitTransaction();

            logDatabaseSave(race, race.getCityLocationRace());
        }
    }

    public void controlAndSave(Teamed team) {
        //This check makes sure that no team with the same idDriver, idConstructor and yearTeamed already exists in the database
        Teamed teamToCheck = getTeamFromIdDriverIdConstructorYearTeamed(team.getIdDriver(), team.getIdConstructor(), team.getYearTeamed());

        if(teamToCheck == null)
        {
            getSession().save(team);
            beginAndCommitTransaction();

            Driver driver = team.getIdDriver();
            Constructor constructor = team.getIdConstructor();

            logDatabaseSave(team, driver.getLastnameDriver() + " " + driver.getFirstnameDriver() + " | " + constructor.getNameConstructor() + " | " + team.getYearTeamed());
        }
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
