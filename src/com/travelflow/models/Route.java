package com.travelflow.models;

public class Route {
    public String routeCode, source, destination, travelType;
    public String classType;  // 🔥 NEW: "Economy", "AC3", "Business", etc.
    public double ticketPrice;  // Base price
    public String departureTime, arrivalTime;
    public int availableSeats;
    public double finalPrice;

    // 🔥 ORIGINAL Constructor (for existing routes)
    public Route(String routeCode, String source, String destination, String travelType,
                 double ticketPrice, String departureTime, String arrivalTime, int availableSeats) {
        this.routeCode = routeCode;
        this.source = source;
        this.destination = destination;
        this.travelType = travelType;
        this.classType = "Standard";  // Default class
        this.ticketPrice = ticketPrice;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
    }

    // 🔥 NEW Constructor (for class routes)
    public Route(String routeCode, String source, String destination, String travelType,
                 String classType, double basePrice, String depTime, String arrTime, int seats) {
        this.routeCode = routeCode;
        this.source = source;
        this.destination = destination;
        this.travelType = travelType;
        this.classType = classType;
        this.ticketPrice = basePrice;  // Base price
        this.departureTime = depTime;
        this.arrivalTime = arrTime;
        this.availableSeats = seats;
    }

    // 🔥 DYNAMIC CLASS PRICING
    public double getClassPrice() {
        double base = this.ticketPrice;
        String type = this.classType.toLowerCase();
        String travel = this.travelType.toLowerCase();

        if ("flight".equals(travel)) {
            return switch (type) {
                case "economy" -> base;
                case "business" -> base * 2.5;
                case "first" -> base * 4.0;
                default -> base;
            };
        } else { // Train
            return switch (type) {
                case "general" -> base * 0.3;
                case "sleeper" -> base * 0.5;
                case "ac3", "3ac" -> base * 0.8;
                case "ac2", "2ac" -> base * 1.2;
                case "ac1", "1ac" -> base * 2.0;
                default -> base;
            };
        }
    }

    @Override
    public String toString() {
        double price = getClassPrice();
        return String.format("%s (%s %s, ₹%.0f, %d seats)",
                routeCode, travelType, classType, price, availableSeats);
    }
}
