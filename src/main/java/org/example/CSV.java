package org.example;

import org.example.DBObjects.Constructor;
import org.example.DBObjects.Driver;
import org.example.DBObjects.Race;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    private final String filename;
    private List<String[]> data;

    private final String tableName;

    private final ORMSession ormSession;

    /**
     * Constructeur de la classe CSV permettant de lire un fichier CSV et d'ajouter les données (reconnues) dans la base de données
     *
     * @param filename  Nom du fichier CSV à parser
     * @param tableName Nom de la table dans laquelle insérer les données.
     */
    public CSV(String filename, String tableName) {
        this.filename = filename;
        this.tableName = tableName;
        this.ormSession = new ORMSession(SOURCE_CSV);
    }

    /**
     * Méthode permettant de lire un fichier CSV et de stocker les données dans une liste de tableaux de String
     */
    private void readCSV() {
        this.data = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;

            // On lit la première ligne du buffer pour ne pas la prendre en compte
            // l'entête du fichier CSV
            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillConstructorTable() {

        for (String[] row : data) {
            String name = row[2];
            String nationality = row[3];

            // On crée un objet Constructor que l'on va insérer directement grâce à Hibernate
            Constructor constructor = new Constructor(name, nationality);

            ormSession.controlAndSave(constructor);
        }

        ormSession.closeSession();
    }

    private void fillDriverTable() {

        for (String[] row : data) {
            String last_name = row[5];
            String first_name = row[4];
            String nationality = row[7];

            // On crée un objet Driver que l'on va insérer directement grâce à Hibernate
            Driver driver = new Driver(last_name, first_name, nationality);

            ormSession.controlAndSave(driver);
        }

        ormSession.closeSession();
    }


    private void fillRaceTable() {
        // On sauvegarde toutes les villes pour éviter de les réinsérer
        List<String> cities = new ArrayList<>();

        for (String[] row : data) {
            String city = row[3];
            String country = row[4];

            if (cities.contains(city)) {
                continue;
            } else {
                cities.add(city);
            }

            // On crée un objet Race que l'on va insérer directement grâce à Hibernate
            Race race = new Race(city, country);

            ormSession.controlAndSave(race);
        }

        ormSession.closeSession();
    }

    public void fillFromFile() {
        this.readCSV();

        switch (this.tableName) {
            case "constructor":
                this.fillConstructorTable();
                break;
            case "driver":
                this.fillDriverTable();
                break;
            case "race":
                this.fillRaceTable();
                break;
            default:
                throw new RuntimeException("Table inconnue");
        }

    }

    //PRIVATE CONSTANTS
    private static final String SOURCE_CSV = "CSV";

}
