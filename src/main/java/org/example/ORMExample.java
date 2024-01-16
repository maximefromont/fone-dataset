package org.example;
import org.hibernate.*;
//Import everythin in the Db-object folder
import org.example.DBObjects.*;

public class ORMExample {

    public static void testORM() {

        //Use ORMSession when you need to access the database
        ORMSession ormSession = new ORMSession();
        Session session = ormSession.getSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            String hql;
            Query query;

            //This bloc of code drops everything in the table
            hql = String.format("delete from %s", "Teamed");
            query = session.createQuery(hql);
            query.executeUpdate();

            //This bloc of code drops everything in the table
            hql = String.format("delete from %s", "Earned");
            query = session.createQuery(hql);
            query.executeUpdate();

            //TEST DRIVER TABLE
            //This bloc of code drops everything in the table
            hql = String.format("delete from %s", "Driver");
            query = session.createQuery(hql);
            query.executeUpdate();

            Driver driver = new Driver("André", "La menace");
            session.save(driver);

            //TEST CONSTRUCTOR TABLE
            //This bloc of code drops everything in the table
            hql = String.format("delete from %s", "Constructor");
            query = session.createQuery(hql);
            query.executeUpdate();

            Constructor constructor = new Constructor("PolytechAuto", "Listenbourg");
            session.save(constructor);

            //TEST RACE TABLE
            //This bloc of code drops everything in the table
            hql = String.format("delete from %s", "Race");
            query = session.createQuery(hql);
            query.executeUpdate();

            Race race = new Race("Circuit du batiment 620");
            session.save(race);

            //TEST TEAMED TABLE
            Teamed teamed = new Teamed(driver.getIdDriver(), constructor.getIdConstructor(), "2024");
            session.save(teamed);

            //TEST EARNED TABLE
            Earned earned = new Earned(driver.getIdDriver(), race.getIdRace(), 15, "2024");
            session.save(earned);

            session.flush() ;
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        ormSession.closeSession();
        ormSession.closeSessionFactory();

    }

}
