package com.travelflow.datastructures;

import com.travelflow.models.Booking;

public class BookingQueue {
    private java.util.ArrayList<Booking> queue = new java.util.ArrayList<>();

    public int size() { return queue.size(); }

    public Booking peek() {
        return queue.isEmpty() ? null : queue.get(0);
    }

    public void enqueue(Booking booking) {
        queue.add(booking);
        System.out.println("⏳ PaymentQueue ENQUEUED: " + booking.bookingId +
                " (Queue: " + queue.size() + " pending)");
    }

    // ✅ FIXED: Use actual booking.finalPrice
    public Booking dequeue() {
        if (!queue.isEmpty()) {
            Booking payment = queue.remove(0);
            System.out.println("💳 PaymentQueue PROCESSED: " + payment.bookingId +
                    " " + payment.finalPrice + " | Remaining: " + queue.size());
            return payment;
        }
        System.out.println("ℹ️  PaymentQueue empty - no pending payments");
        return null;
    }
}
