package org.example;

public class Main {
    public static void main(String[] args) {
        MenuController.startMenu();

        ORMExample.testORM();

        CSV csv = new CSV("src/main/java/org/example/dataset/circuits.csv", "race"); // Ugly path
        csv.fillFromFile();
    }
}