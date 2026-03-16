package com.travelflow.datastructures;

import com.travelflow.models.Booking;
import java.util.LinkedList;

public class BookingHistoryList {
    private LinkedList<Booking> history = new LinkedList<>();
    private int maxSize = 10;

    public int size() {
        return history.size();
    }

    public Booking peek() {
        if (!history.isEmpty()) {
            return history.getFirst();
        }
        return null;
    }

    public void addBooking(Booking booking) {
        history.addFirst(booking);

        if (history.size() > maxSize) {
            history.removeLast();
            System.out.println("📜 History full - oldest booking removed (keeping last 10)");
        }

        System.out.println("📜 HistoryList ADDED: " + booking.bookingId +
                " (Total: " + history.size() + "/" + maxSize + ")");
    }

    public String getRecentBookings() {
        if (history.isEmpty()) {
            return "No bookings yet";
        }

        StringBuilder recent = new StringBuilder("Recent: ");
        for (int i = 0; i < Math.min(5, history.size()); i++) {
            Booking b = history.get(i);
            recent.append(b.bookingId).append(" ");
        }
        recent.append(history.size() > 5 ? "..." : "");
        return recent.toString();
    }
}
