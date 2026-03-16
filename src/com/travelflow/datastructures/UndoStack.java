package com.travelflow.datastructures;

import com.travelflow.models.Booking;
import java.util.Stack;

public class UndoStack {
    private Stack<Booking> stack = new Stack<>();

    public int size() {
        return stack.size();
    }

    public Booking peek() {
        if (!stack.isEmpty()) {
            return stack.peek();
        }
        return null;
    }

    public void push(Booking booking) {
        stack.push(booking);
        System.out.println("↩️  UndoStack PUSHED: " + booking.bookingId +
                " (Stack size: " + stack.size() + ")");
    }

    public Booking pop() {
        if (!stack.isEmpty()) {
            Booking booking = stack.pop();
            System.out.println("🔄 UNDO POPPED: " + booking.bookingId +
                    " (Remaining: " + stack.size() + ")");
            return booking;
        }
        System.out.println("ℹ️  UndoStack empty - nothing to undo");
        return null;
    }
}
