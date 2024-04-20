package org.example;

import java.util.Scanner;

public class MenuController {

    //TODO : Change variables, menus strings, and methods names for better readability (instead of "menu item" ...)
    private static F1Scaper f1Scaper = new F1Scaper();

    public static void startMenu() {

        System.out.println("\nYou are at the main menu.\n");

        Scanner scanner = new Scanner(System.in);
        int userChoice = -1; //-1 == error

        System.out.println("Appication menu :");
        System.out.println("1. Fill database.");
        System.out.println("2. Search database.");
        System.out.println("3. Pre-made database queries.");
        System.out.println("99. Exit application");
        System.out.println();
        System.out.print("Please enter your choice : ");
        userChoice = scanner.nextInt();

        System.out.println(); //Empty line for interface readability

        //Menu switchs
        switch (userChoice) {
            case MENU_ITEM_1 -> handleMenuItem1();
            case MENU_ITEM_2 -> handleMenuItem2();
            case MENU_ITEM_3 -> handleMenuItem3();
            case MENU_EXIT -> handleExit();
        }
    }

    private static void handleMenuItem1() {
        //Code action 1 here, call a controller or instanciate a specific class for complex action
        System.out.println("You have selected 'Fill database'.\n");

        Scanner scanner = new Scanner(System.in);
        int userChoice = -1; //-1 == error

        System.out.println("Fill database menu :");
        System.out.println("11. Fill database with datas from scrapped websites.");
        System.out.println("12. Fill database with datas from local CSV files.");
        System.out.println("13. Fill database with datas from Ergast API (Please note that this put a strain on the free public API).");
        System.out.println("99. Exit application");
        System.out.println();
        System.out.print("Please enter your choice : ");
        userChoice = scanner.nextInt();

        System.out.println(); //Empty line for interface readability

        //Menu switchs
        switch (userChoice) {
            case MENU_ITEM_11 -> handleMenuItem11();
            case MENU_ITEM_12 -> handleMenuItem12();
            case MENU_ITEM_13 -> handleMenuItem13();
            case MENU_EXIT -> handleExit();
        }
    }

    private static void handleMenuItem2() {
        //Code action 2 here, call a controller or instanciate a specific class for complex action
        System.out.println("You have selected menu item 2");
    }

    private static void handleMenuItem3() {
        //Code action 3 here, call a controller or instanciate a specific class for complex action
        System.out.println("You have selected menu item 3");
    }

    private static void handleMenuItem11(){
        //Code action 11 here, call a controller or instanciate a specific class for complex action
        System.out.println("You have selected 'Fill database with datas from scrapped websites'.\n");

        //Init and launch scrapper

        startMenu(); //And back to start menu
    }

    private static void handleMenuItem12() {
        //Code action 12 here, call a controller or instanciate a specific class for complex action
        System.out.println("You have selected 'Fill database with datas from local CSV files'.\n");

        //Init and launch CSV parser
        CSV csv = new CSV("src/main/java/org/example/dataset/drivers.csv", "driver"); // Ugly path
        csv.fillFromFile();

        CSV csv2 = new CSV("src/main/java/org/example/dataset/constructors.csv", "constructor"); // Ugly path
        csv2.fillFromFile();

        CSV csv3 = new CSV("src/main/java/org/example/dataset/circuits.csv", "race"); // Ugly path
        csv3.fillFromFile();

        startMenu(); //And back to start menu
    }

    private static void handleMenuItem13() {
        //Code action 13 here, call a controller or instanciate a specific class for complex action
        System.out.println("You have selected 'Fill database with datas from Ergast API'.\n");

        Scanner scanner = new Scanner(System.in);
        int userChoice = -1; //-1 == error

        int driverDataLimit = 100; //Default = 100 for less strain on the API
        int constructorDataLimit = 100; //Default = 100 for less strain on the API
        int raceDataLimit = 100; //Default = 100 for less strain on the API
        boolean getAllTeamData = false; //Defautl = false for less strain on the API
        int teamDataYear = 2022; //Default = 2022 for modern datas
        int teamDataLimit = 100;

        System.out.println("How much driver datas do you want to fetch from the API (Default = 100) : ");
        driverDataLimit = scanner.nextInt();
        System.out.println("How much constructor datas do you want to fetch from the API (Default = 100) : ");
        constructorDataLimit = scanner.nextInt();
        System.out.println("How much race datas do you want to fetch from the API (Default = 100) : ");
        raceDataLimit = scanner.nextInt();
        System.out.println("Do you want to fetch all team datas from the API (false|true) (Default = false, please note that activating this option will take a long time and put a lot of strain on the API) : ");
        getAllTeamData = scanner.nextBoolean();
        if(getAllTeamData){
            System.out.println("Please enter the year for the team datas (Default = 2022) : ");
            teamDataYear = scanner.nextInt();
            System.out.println("How much team datas do you want to fetch from the API (Default = 100) : ");
            teamDataLimit = scanner.nextInt();
        }

        //Init and launch API fetcher
        //Test api
        ErgastAPIFetcher.init();
        ErgastAPIFetcher.fillConstructors(constructorDataLimit);
        ErgastAPIFetcher.fillDrivers(driverDataLimit);
        ErgastAPIFetcher.fillRaces(raceDataLimit);
        if(getAllTeamData){
            ErgastAPIFetcher.fillAllTeamed(); //Please be careful with this method as it puts a lot of strain on the free and public API
        }
        else{
            ErgastAPIFetcher.fillTeamed(teamDataYear, teamDataLimit);
        }

        startMenu(); //And back to start menu
    }

    private static void handleExit() {
        System.out.println("Exiting application...");
        System.exit(0);
    }

    //Changes constants names for better readability (ITEM_1 -> LOAD_DATA for example)
    public static final int MENU_ITEM_1 = 1;
    public static final int MENU_ITEM_2 = 2;
    public static final int MENU_ITEM_3 = 3;
    public static final int MENU_ITEM_11 = 11;
    public static final int MENU_ITEM_12 = 12;
    public static final int MENU_ITEM_13 = 13;
    public static final int MENU_EXIT = 99;
    //...

}
