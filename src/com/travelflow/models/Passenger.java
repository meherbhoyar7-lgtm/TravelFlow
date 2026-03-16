package com.travelflow.models;

public class Passenger {
    public String name, age, gender, seatNumber;

    public Passenger(String name, String age, String gender, String seatNumber) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return name + " (" + age + ", " + gender + ") - " + seatNumber;
    }
}
