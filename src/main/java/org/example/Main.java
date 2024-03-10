package org.example;

public class Main {
    public static void main(String[] args) {
//        MenuController.startMenu();
//
//        ORMExample.testORM();

        //CSV csv = new CSV("src/main/java/org/example/dataset/drivers.csv", "driver"); // Ugly path
        //csv.fillFromFile();

        //Test api
        ErgastAPIFetcher fetcher = new ErgastAPIFetcher();
        fetcher.fillConstructors();
        fetcher.fillDrivers();
        fetcher.fillRaces();
    }
}