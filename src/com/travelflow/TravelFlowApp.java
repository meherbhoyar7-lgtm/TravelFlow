package com.travelflow;

import com.travelflow.services.*;
import com.travelflow.utils.*;

public class TravelFlowApp {
    public static void main(String[] args) {
        System.out.println("🚀 TravelFlow - Smart Travel Booking System");
        DataInitializer.init();

        ConsoleMenu menu = new ConsoleMenu();
        menu.showMenu();
    }
}