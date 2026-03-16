package com.travelflow.utils;

import com.travelflow.models.*;
import com.travelflow.services.*;
import com.travelflow.datastructures.LinearSearch;
import java.util.Scanner;
import java.util.ArrayList;

public class ConsoleMenu {
    private Scanner scanner = new Scanner(System.in);
    private String currentUserId = null;

    public static void main(String[] args) {
        clearScreen();
        DataInitializer.init();
        new ConsoleMenu().showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        printBanner();
        System.out.println("\n🌟 Welcome to TravelFlow - India's Smartest Travel Partner! 🌟");
        System.out.println("✈️  170+ Routes | 🚂 Premium Classes | 💳 Instant Booking");
        pressEnter();
        showMenu();
    }

    private void printBanner() {
        System.out.println("\n" +
                "╔══════════════════════════════════════════════════════╗\n" +
                "║  ✈️  TRAVELFLOW  |  Your Journey Starts Here!  🚂  ║\n" +
                "╚══════════════════════════════════════════════════════╝");
    }

    public void showMenu() {
        while (true) {
            if (currentUserId == null) {
                showLoginScreen();
            } else {
                showTravelerDashboard();
            }
        }
    }

    // ✨ REAL AIRLINE-STYLE LOGIN
    private void showLoginScreen() {
        clearScreen();
        printBanner();
        System.out.println("\n👤  Choose Your Journey");
        System.out.println("═══════════════════════════════");
        System.out.println("  [1]  👨‍👩‍👧 New Traveler (Register)");
        System.out.println("  [2]  👨‍💼 Returning Traveler (Login)");
        System.out.println("  [0]  ❌ Exit");
        System.out.print("\n✈️  Select → ");

        int choice = getChoice(0, 2);
        switch (choice) {
            case 1 -> registerTraveler();
            case 2 -> loginTraveler();
            case 0 -> {
                System.out.println("\n👋 Safe travels! Book again soon! ✈️");
                System.exit(0);
            }
        }
    }

    private void registerTraveler() {
        clearScreen();
        printBanner();
        System.out.println("\n✨ Create Your Travel Profile");
        System.out.println("═══════════════════════════════");

        System.out.print("👤 Full Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("📧 Email: ");
        String email = scanner.nextLine().trim();

        String userId = "U" + String.format("%03d", BookingService.getUsers().size() + 1);
        User newUser = new User(userId, name, email);
        BookingService.getUsers().add(newUser);
        currentUserId = userId;

        clearScreen();
        printBanner();
        System.out.println("\n🎉 Welcome aboard, " + name + "!");
        System.out.println("═══════════════════════════════");
        System.out.println("✈️  Traveler ID: " + userId);
        System.out.println("📱 Profile Created Successfully!");
        System.out.println("\n💡 Pro Tip: Use your Traveler ID for quick login next time!");
        pressEnter();
    }

    private void loginTraveler() {
        clearScreen();
        printBanner();
        System.out.println("\n🔑 Quick Login");
        System.out.println("═══════════════════════");
        System.out.print("🆔 Enter Traveler ID: ");
        currentUserId = scanner.nextLine().trim();

        String userName = getCurrentUserName();
        clearScreen();
        printBanner();
        System.out.println("\n✅ Welcome back, " + userName + "!");
        System.out.println("═══════════════════════════════");
        System.out.println("✈️  Logged in as: " + currentUserId);
        pressEnter();
    }

    // ✨ PRODUCTION-GRADE DASHBOARD
    private void showTravelerDashboard() {
        clearScreen();
        printBanner();
        System.out.println("\n👨‍👩‍👧 " + getCurrentUserName() + " | " + currentUserId);
        System.out.println("═══════════════════════════════════════════════════════");

        // LIVE TRAVEL STATUS
        int history = BookingService.getHistory().size();
        int payments = BookingService.getPaymentQueue().size();
        int refunds = BookingService.getRefundQueue().size();
        int undos = BookingService.getUndoStack().size();

        System.out.println("✈️  Your Travel Status");
        System.out.println("═══════════════════════════════");
        System.out.printf("📜 Total Trips: %-2d    ⏳ Payments Pending: %-2d%n", history, payments);
        System.out.printf("💰 Refunds Queued: %-2d    🔄 Undo Available: %-2d%n", refunds, undos);

        if (history > 0) {
            System.out.println("🎫 Recent Trip: " + BookingService.getHistory().getRecentBookings());
        }
        System.out.println("\n🌟 Your Options");
        System.out.println("═══════════════════════════════════════");
        System.out.println("  [1]  🔍 Find Flights & Trains");
        System.out.println("  [2]  📱 Instant Booking");
        System.out.println("  [3]  ❌ Manage Bookings");
        System.out.println("  [4]  💳 Process Payment");
        System.out.println("  [5]  💸 Claim Refund");
        System.out.println("  [6]  🔄 Undo Last Booking");
        System.out.println("  [7]  📊 Travel Summary");
        System.out.println("  [0]  🚪 Logout");
        System.out.print("\n✈️  Choose → ");

        int choice = getChoice(0, 7);
        handleChoice(choice);
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> findJourney();
            case 2 -> bookJourney();
            case 3 -> CancellationService.cancelInteractive();
            case 4 -> processPayment();
            case 5 -> processRefund();
            case 6 -> undoBooking();
            case 7 -> showTravelSummary();
            case 0 -> {
                currentUserId = null;
                System.out.println("\n👋 Happy travels!");
                pressEnter();
            }
        }
    }

    // ✨ REALISTIC INTERACTIVE FLOWS
    private void findJourney() {
        clearScreen();
        printBanner();
        System.out.println("\n🔍 Journey Finder");
        System.out.println("═══════════════════════════════════════");
        System.out.print("🌍 From (City): ");
        String from = scanner.nextLine().trim().toLowerCase();
        System.out.print("🛫 To (City): ");
        String to = scanner.nextLine().trim().toLowerCase();

        System.out.println("\n⏳ Searching " + from.toUpperCase() + " → " + to.toUpperCase() + "...");
        SearchService.searchTravelOptions(from, to);
        pressEnter();
    }

    private void bookJourney() {
        System.out.print("\n🌍 From: ");
        String from = scanner.nextLine().trim().toLowerCase();
        System.out.print("🛫 To: ");
        String to = scanner.nextLine().trim().toLowerCase();

        SearchService.searchTravelOptions(from, to);
        System.out.print("\n🎫 Select route number: ");

        try {
            int option = Integer.parseInt(scanner.nextLine().trim());
            ArrayList<Route> results = LinearSearch.searchRoutes(BookingService.getRoutes(), from, to);

            if (option < 1 || option > results.size()) {
                System.out.println("❌ Invalid route!");
                pressEnter();
                return;
            }

            Route route = results.get(option - 1);
            System.out.print("👤 Seats needed (max " + route.availableSeats + "): ");
            int seats = Integer.parseInt(scanner.nextLine().trim());

            if (seats <= 0 || seats > route.availableSeats) {
                System.out.println("❌ Invalid seats!");
                pressEnter();
                return;
            }

            ArrayList<Passenger> passengers = collectPassengerDetails(seats, route.routeCode);

            if (BookingService.confirmBooking(currentUserId, route.routeCode, seats, passengers)) {
                System.out.println("\n🎉 Booking Confirmed! Your ticket is ready! 🧾");
                System.out.println("💳 Payment queued | 🔄 Undo available | 📜 Saved to profile");
            }
        } catch (Exception e) {
            System.out.println("❌ Please enter valid numbers!");
        }
        pressEnter();
    }

    private void processPayment() {
        System.out.println("\n💳 Processing Payments...");
        BookingService.processPayments();
        System.out.println("✅ Payment completed! Safe travels!");
        pressEnter();
    }

    private void processRefund() {
        System.out.println("\n💸 Processing Refunds...");
        CancellationService.processRefunds();
        pressEnter();
    }

    private void undoBooking() {
        if (BookingService.undoLastBooking()) {
            System.out.println("\n✅ Booking cancelled! Full refund queued.");
        } else {
            System.out.println("\n❌ No bookings to undo.");
        }
        pressEnter();
    }

    private void showTravelSummary() {
        clearScreen();
        printBanner();
        System.out.println("\n📊 Travel Summary - " + getCurrentUserName());
        System.out.println("═══════════════════════════════════════");
        System.out.println("📜 History: " + BookingService.getHistory().size() + " trips");
        System.out.println("⏳ Payments: " + BookingService.getPaymentQueue().size() + " pending");
        System.out.println("💰 Refunds: " + BookingService.getRefundQueue().size() + " queued");
        System.out.println("🔄 Undo: " + BookingService.getUndoStack().size() + " available");
        System.out.println("\n🎫 Recent: " + BookingService.getHistory().getRecentBookings());
        System.out.println("\n💡 All data saved to TravelFlow_Reports.csv");
        pressEnter();
    }

    // UTILITY METHODS
    private int getChoice(int min, int max) {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return (choice >= min && choice <= max) ? choice : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private void pressEnter() {
        System.out.print("\n⏸️  Press Enter to continue... ");
        scanner.nextLine();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");  // ✅ COMPLETE LINE
    }

    private ArrayList<Passenger> collectPassengerDetails(int seats, String routeCode) {
        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < seats; i++) {
            System.out.println("\n👤 Passenger " + (i + 1) + "/" + seats);
            System.out.print("  Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = routeCode + "-Pax" + (i + 1);

            System.out.print("  Age: ");
            String age = scanner.nextLine().trim();
            if (age.isEmpty()) age = "25";

            System.out.print("  Gender (M/F): ");
            String gender = scanner.nextLine().trim().toUpperCase();
            if (gender.isEmpty()) gender = "M";

            passengers.add(new Passenger(name, age, gender, routeCode + "-S" + (i + 1)));
        }
        return passengers;
    }

    private String getCurrentUserName() {
        for (User u : BookingService.getUsers()) {
            if (u.id.equals(currentUserId)) return u.name;
        }
        return currentUserId != null ? currentUserId : "Traveler";
    }
}

