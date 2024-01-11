package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    /***
     * Méthode permettant de se connecter à la base de données
     * @return Connexion à la base de données
     * @throws RuntimeException si la connexion échoue
     */
    public static Connection getConnection() {
        String DATABASE_NAME = "postgres";
        String DATABASE_HOST = "localhost";
        int DATABASE_PORT = 5432;

        String URL = "jdbc:postgresql://" + DATABASE_HOST + ":" + DATABASE_PORT + "/" + DATABASE_NAME;

        try {
            String DATABASE_USER = "postgres";
            String DATABASE_PASSWORD = "postgres";
            return DriverManager.getConnection(URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
