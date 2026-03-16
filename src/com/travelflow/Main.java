package com.travelflow;

import com.travelflow.utils.ConsoleMenu;
import com.travelflow.utils.DataInitializer;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 TravelFlow - Professional Booking System");
        System.out.println("==========================================");

        DataInitializer.init();

        ConsoleMenu menu = new ConsoleMenu();
        menu.showMenu();
    }
}
