package org.example;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
//Import everythin in the Db-object folder
import org.example.DBObjects.*;

public class ORMExample {

    public static void testORM() {

        Configuration config = new Configuration();
        config.addClass(Driver.class);
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Driver driver = new Driver("André", "La menace");
            session.save(driver);
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

        sessionFactory.close();

    }

}
