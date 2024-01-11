package org.example;

public class Main {
    public static void main(String[] args) {
        CSV csv = new CSV("src/main/java/org/example/dataset/constructors.csv", "constructor"); // Ugly path
        csv.fillFromFile();
    }


}