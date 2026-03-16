package com.travelflow.utils;

import com.travelflow.models.*;
import com.travelflow.services.BookingService;
import java.util.ArrayList;

public class DataInitializer {
    public static void init() {
        var routes = BookingService.getRoutes();
        routes.clear();
        BookingService.getUsers().clear();

        System.out.println("✈️ Loading TravelFlow Routes...");

        // 🔥================= DELHI → MUMBAI (10 Options) =================
        System.out.println("  Adding Delhi → Mumbai routes...");
        routes.add(new Route("DEL-MUM-IG001-E", "Delhi", "Mumbai", "Flight", "Economy", 4500, "06:30", "08:45", 180));
        routes.add(new Route("DEL-MUM-IG001-B", "Delhi", "Mumbai", "Flight", "Business", 4500, "06:30", "08:45", 50));
        routes.add(new Route("DEL-MUM-VS002-E", "Delhi", "Mumbai", "Flight", "Economy", 4800, "14:00", "16:15", 165));
        routes.add(new Route("DEL-MUM-VS002-P", "Delhi", "Mumbai", "Flight", "Premium Economy", 4800, "14:00", "16:15", 30));
        routes.add(new Route("DEL-MUM-AI003-E", "Delhi", "Mumbai", "Flight", "Economy", 4200, "22:00", "00:20", 200));
        routes.add(new Route("DEL-MUM-RJ001-2A", "Delhi", "Mumbai", "Train", "AC2", 2160, "17:30", "10:15", 36));
        routes.add(new Route("DEL-MUM-RJ001-3A", "Delhi", "Mumbai", "Train", "AC3", 1440, "17:30", "10:15", 72));
        routes.add(new Route("DEL-MUM-DR002-SL", "Delhi", "Mumbai", "Train", "Sleeper", 900, "20:00", "09:30", 120));
        routes.add(new Route("DEL-MUM-DR002-3A", "Delhi", "Mumbai", "Train", "AC3", 1320, "20:00", "09:30", 60));
        routes.add(new Route("DEL-MUM-MM003-G", "Delhi", "Mumbai", "Train", "General", 540, "23:00", "12:30", 200));

        // 🔥================= MUMBAI → DELHI (10 Options) =================
        System.out.println("  Adding Mumbai → Delhi routes...");
        routes.add(new Route("MUM-DEL-IG101-E", "Mumbai", "Delhi", "Flight", "Economy", 4500, "06:00", "08:15", 185));
        routes.add(new Route("MUM-DEL-IG101-B", "Mumbai", "Delhi", "Flight", "Business", 4500, "06:00", "08:15", 42));
        routes.add(new Route("MUM-DEL-VS202-E", "Mumbai", "Delhi", "Flight", "Economy", 4650, "13:45", "16:00", 170));
        routes.add(new Route("MUM-DEL-VS202-P", "Mumbai", "Delhi", "Flight", "Premium Economy", 4650, "13:45", "16:00", 28));
        routes.add(new Route("MUM-DEL-AI104-E", "Mumbai", "Delhi", "Flight", "Economy", 4400, "21:30", "23:45", 195));
        routes.add(new Route("MUM-DEL-RJ201-2A", "Mumbai", "Delhi", "Train", "AC2", 2160, "16:45", "09:30", 36));
        routes.add(new Route("MUM-DEL-RJ201-3A", "Mumbai", "Delhi", "Train", "AC3", 1440, "16:45", "09:30", 72));
        routes.add(new Route("MUM-DEL-DR302-SL", "Mumbai", "Delhi", "Train", "Sleeper", 900, "19:15", "12:00", 120));
        routes.add(new Route("MUM-DEL-DR302-3A", "Mumbai", "Delhi", "Train", "AC3", 1320, "19:15", "12:00", 60));
        routes.add(new Route("MUM-DEL-MD403-G", "Mumbai", "Delhi", "Train", "General", 540, "22:30", "15:15", 200));

        // 🔥 Quick other routes
        System.out.println("  Adding popular routes...");
        routes.add(new Route("DEL-BLR-IG501", "Delhi", "Bangalore", "Flight", 5200, "07:00", "09:30", 160));
        routes.add(new Route("HYD-DEL-VS601", "Hyderabad", "Delhi", "Flight", 4900, "09:00", "11:00", 165));
        routes.add(new Route("MUM-BLR-IX701", "Mumbai", "Bangalore", "Flight", 3800, "07:45", "09:00", 190));

        int total = routes.size();
        int flights = countFlights(routes);
        int trains = countTrains(routes);

        System.out.println("\n✅ Loaded " + total + " routes!");
        System.out.println("   ✈️ " + flights + " Flights | 🚂 " + trains + " Trains");
    }

    private static int countFlights(ArrayList<Route> routes) {
        return (int) routes.stream().filter(r -> r.travelType.equals("Flight")).count();
    }

    private static int countTrains(ArrayList<Route> routes) {
        return (int) routes.stream().filter(r -> r.travelType.equals("Train")).count();
    }
}
