package org.example;

import java.util.Scanner;

public class MenuController {

    //TODO : Change variables, menus strings, and methods names for better readability (instead of "menu item" ...)

    public static void startMenu() {

        Scanner scanner = new Scanner(System.in);
        int userChoice = -1; //-1 == error

        System.out.println("Appication menu :");
        System.out.println("1. Menu item 1");
        System.out.println("2. Menu item 2");
        System.out.println("3. Menu item 3");
        System.out.println("99. Exit application");
        System.out.println("");
        System.out.print("Please enter your choice : ");
        userChoice = scanner.nextInt();

        System.out.println(""); //Empty line for interface readability

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
        System.out.println("You have selected menu item 1");
    }

    private static void handleMenuItem2() {
        //Code action 2 here, call a controller or instanciate a specific class for complex action
        System.out.println("You have selected menu item 2");
    }

    private static void handleMenuItem3() {
        //Code action 3 here, call a controller or instanciate a specific class for complex action
        System.out.println("You have selected menu item 3");
    }

    private static void handleExit() {
        System.out.println("Exiting application...");
        System.exit(0);
    }

    //Changes constants names for better readability (ITEM_1 -> LOAD_DATA for example)
    public static final int MENU_ITEM_1 = 1;
    public static final int MENU_ITEM_2 = 2;
    public static final int MENU_ITEM_3 = 3;
    public static final int MENU_EXIT = 99;
    //...

}
