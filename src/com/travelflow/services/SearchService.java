package com.travelflow.services;

import com.travelflow.datastructures.LinearSearch;
import com.travelflow.models.Route;
import com.travelflow.services.BookingService;
import java.util.ArrayList;

public class SearchService {
    public static void searchTravelOptions(String fromCity, String toCity) {
        ArrayList<Route> results = LinearSearch.searchRoutes(BookingService.getRoutes(), fromCity, toCity);

        if (results.isEmpty()) {
            System.out.println("\n❌ No routes found for " + fromCity.toUpperCase() + " → " + toCity.toUpperCase());
            return;
        }

        System.out.println("\n✈️🚂 " + results.size() + " OPTIONS (" + fromCity.toUpperCase() + " → " + toCity.toUpperCase() + "):");
        System.out.println("═══════════════════════════════════════════════════════════════════════");

        String lastCompany = "";
        for (int i = 0; i < results.size(); i++) {
            Route route = results.get(i);
            String company = extractCompany(route.routeCode);

            if (!company.equals(lastCompany)) {
                System.out.println();
                System.out.println(getCompanyEmoji(route) + " " + company.toUpperCase() + ":");
                lastCompany = company;
            }

            // ✅ FIXED: route.price instead of route.basePrice
            String price = formatPrice(route.finalPrice, route.classType);
            String seats = String.format("%4s", route.availableSeats);
            String routeDisplay = getRouteDisplay(fromCity, toCity);

            System.out.printf("%d. 🎫 %-13s | %-20s | %-14s | %8s | %5s→%5s (%s)%n",
                    i+1, route.routeCode, routeDisplay,
                    route.travelType + " " + route.classType, price,
                    route.departureTime, route.arrivalTime, seats);
        }
        System.out.println("═══════════════════════════════════════════════════════════════════════");
    }

    private static String getCompanyEmoji(Route route) {
        if (route.travelType.equals("Flight")) return "✈️";
        return "🚂";
    }

    private static String extractCompany(String routeCode) {
        if (routeCode.contains("IG")) return "IndiGo";
        if (routeCode.contains("VS")) return "Vistara";
        if (routeCode.contains("AI")) return "Air India";
        if (routeCode.contains("RJ")) return "Rajdhani";
        if (routeCode.contains("DR")) return "Duronto";
        if (routeCode.contains("MM") || routeCode.contains("MD")) return "Mail Express";
        return "Indian Railways";
    }

    // ✅ FIXED: Uses route.price (correct field name)
    private static String formatPrice(double basePrice, String classType) {
        double multiplier = getPriceMultiplier(classType);
        int finalPrice = (int) (basePrice * multiplier);
        return "₹" + String.format("%,d", finalPrice);
    }

    private static String getRouteDisplay(String fromCity, String toCity) {
        String fromCode = getCityCode(fromCity);
        String toCode = getCityCode(toCity);
        return fromCode + "→" + toCode;
    }

    private static String getCityCode(String city) {
        String cleanCity = city.toUpperCase().trim();
        return cleanCity.length() > 3 ? cleanCity.substring(0, 3) : cleanCity;
    }

    private static double getPriceMultiplier(String classType) {
        return switch (classType.toLowerCase()) {
            case "first", "1" -> 4.0;
            case "business", "b" -> 2.5;
            case "premium economy", "p" -> 1.6;
            case "ac1", "1a" -> 2.0;
            case "ac2", "2a" -> 1.2;
            case "ac3", "3a", "3e" -> 0.8;
            case "sleeper", "sl" -> 0.5;
            case "general", "g" -> 0.3;
            default -> 1.0;
        };
    }
}
