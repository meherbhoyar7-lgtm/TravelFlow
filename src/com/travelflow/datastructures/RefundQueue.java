package com.travelflow.datastructures;

import com.travelflow.models.Booking;
import java.util.ArrayList;

public class RefundQueue {
    private ArrayList<Booking> queue = new ArrayList<>();
    private int maxSize = 10;

    public int size() {
        return queue.size();
    }

    public Booking peek() {
        if (!queue.isEmpty()) {
            return queue.get(0);
        }
        return null;
    }

    public void enqueue(Booking booking) {
        if (queue.size() < maxSize) {
            queue.add(booking);  // Normal FIFO
            System.out.println("💰 Refund queued: " + booking.bookingId + " (Queue: " + queue.size() + "/" + maxSize + ")");
        } else {
            queue.remove(0);
            queue.add(booking);
            System.out.println("💰 Refund queued (CIRCULAR): " + booking.bookingId + " (Oldest removed)");
        }
    }

    public Booking dequeue() {
        if (!queue.isEmpty()) {
            Booking refund = queue.remove(0);
            // ✅ FIXED: Use booking.finalPrice instead of recalculation
            System.out.println("💸 Refund PROCESSED: " + refund.bookingId + " " +
                    (refund.finalPrice != null ? refund.finalPrice : "₹0"));
            return refund;
        }
        System.out.println("ℹ️  No pending refunds");
        return null;
    }

    private double getRoutePrice(String routeCode) {
        return routeCode.contains("F") ? 3500.0 : 2500.0;
    }
}
