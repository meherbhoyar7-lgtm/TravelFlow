package com.travelflow.models;

import java.util.ArrayList;

public class Booking {
    public String bookingId, userId, routeCode, travelType, status = "confirmed";
    public int seats;
    public ArrayList<Passenger> passengers;
    public String finalPrice;

    // ✅ FIXED: Main constructor with all 6 parameters
    public Booking(String bookingId, String userId, String routeCode, String travelType,
                   int seats, ArrayList<Passenger> passengers) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.routeCode = routeCode;
        this.travelType = travelType;
        this.seats = seats;
        this.passengers = passengers;
    }

    // ✅ Secondary constructor
    public Booking(String bookingId, String userId, String routeCode, int seats, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.routeCode = routeCode;
        this.seats = seats;
        this.status = status;
    }
}
