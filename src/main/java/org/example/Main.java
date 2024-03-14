package org.example;

import java.security.spec.ECGenParameterSpec;

public class Main {
    public static void main(String[] args) {

        CSV csv = new CSV("src/main/java/org/example/dataset/drivers.csv", "driver"); // Ugly path
        csv.fillFromFile();

        CSV csv2 = new CSV("src/main/java/org/example/dataset/constructors.csv", "constructor"); // Ugly path
        csv2.fillFromFile();

        CSV csv3 = new CSV("src/main/java/org/example/dataset/circuits.csv", "race"); // Ugly path
        csv3.fillFromFile();

        //Test api
        //ErgastAPIFetcher.init();
        //ErgastAPIFetcher.fillConstructors();
        //ErgastAPIFetcher.fillDrivers();
        //ErgastAPIFetcher.fillRaces();
        //ErgastAPIFetcher.fillTeamed(2023);
        //ErgastAPIFetcher.fillAllTeamed(); //Please be careful with this as it puts a lot of strain on the API
    }
}