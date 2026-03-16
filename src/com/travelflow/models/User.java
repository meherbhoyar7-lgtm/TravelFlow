package com.travelflow.models;

import java.util.ArrayList;

public class User {
    public String id, name, email;
    public ArrayList<String> bookingHistory = new ArrayList<>();

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void addBooking(String bookingId) {
        bookingHistory.add(bookingId);
    }
}
