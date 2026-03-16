package com.travelflow.services;

import com.travelflow.datastructures.*;
import com.travelflow.models.*;
import com.travelflow.utils.CSVGenerator;
import java.util.ArrayList;

public class BookingService {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private static ArrayList<Route> routes = new ArrayList<>();

    // 🔥 LIVE DSA STRUCTURES
    public static final BookingHistoryList history = new BookingHistoryList();
    public static final BookingQueue paymentQueue = new BookingQueue();
    public static final UndoStack undoStack = new UndoStack();
    public static final RefundQueue refundQueue = new RefundQueue();

    static {
        routes.add(new Route("DEL-MUM-F001-E", "Delhi", "Mumbai", "Flight", 4500.0, "06:30", "08:45", 180));
        routes.add(new Route("DEL-MUM-F001-B", "Delhi", "Mumbai", "Flight", 11250.0, "06:30", "08:45", 50));
        routes.add(new Route("DEL-MUM-T002-G", "Delhi", "Mumbai", "Train", 540.0, "20:00", "07:30", 120));
        routes.add(new Route("DEL-MUM-T002-SL", "Delhi", "Mumbai", "Train", 900.0, "20:00", "07:30", 72));
        routes.add(new Route("DEL-MUM-T002-3A", "Delhi", "Mumbai", "Train", 1440.0, "20:00", "07:30", 54));
    }

    public static boolean confirmBooking(String userId, String routeCode, int seats, ArrayList<Passenger> passengers) {
        Route selectedRoute = null;
        for (Route r : getRoutes()) {
            if (r.routeCode.equals(routeCode)) {
                selectedRoute = r;
                break;
            }
        }

        if (selectedRoute == null || seats > selectedRoute.availableSeats) {
            System.out.println("❌ Route '" + routeCode + "' not found!");
            return false;
        }

        // ✅ FIXED: Dynamic pricing + correct constructor
        double finalPrice = selectedRoute.getClassPrice() * seats;
        String classType = extractClassFromCode(routeCode);

        System.out.println("✅ " + classType + " Class | ₹" + selectedRoute.getClassPrice() +
                "/seat × " + seats + " = ₹" + finalPrice);

        String bookingId = "B" + String.format("%04d", getBookings().size() + 1);
        // ✅ FIXED: Correct 6-parameter constructor
        Booking booking = new Booking(bookingId, userId, routeCode, selectedRoute.travelType, seats, passengers);
        booking.finalPrice = "₹" + String.format("%.0f", finalPrice);

        // Add to ALL 4 DSA structures
        getBookings().add(booking);
        history.addBooking(booking);
        paymentQueue.enqueue(booking);
        undoStack.push(booking);

        CSVGenerator.logAction("BOOKING", userId, bookingId, routeCode, seats, finalPrice, "CONFIRMED");
        showDSADemoStatus(bookingId);
        return true;
    }

    private static String extractClassFromCode(String routeCode) {
        if (routeCode.contains("-E")) return "Economy";
        if (routeCode.contains("-B")) return "Business";
        if (routeCode.contains("-G")) return "General";
        if (routeCode.contains("-SL")) return "Sleeper";
        if (routeCode.contains("-3A")) return "AC3";
        if (routeCode.contains("-2A")) return "AC2";
        if (routeCode.contains("-1A")) return "AC1";
        return "Standard";
    }

    private static void showDSADemoStatus(String bookingId) {
        System.out.println("\n📊 LIVE DSA STRUCTURES:");
        System.out.println("═══════════════════════");
        System.out.println("📜 History: " + history.size());
        System.out.println("⏳ Payments: " + paymentQueue.size());
        System.out.println("↶ Undo: " + undoStack.size());
        System.out.println("💰 Refunds: " + refundQueue.size());
        System.out.println("🎫 Latest: " + bookingId);
        System.out.println("═══════════════════════");
    }

    public static void processPayments() {
        Booking payment = paymentQueue.dequeue();
        if (payment != null) {
            System.out.println("💳 PROCESSED: " + payment.bookingId);
        } else {
            System.out.println("ℹ️ No pending payments");
        }
    }

    public static boolean undoLastBooking() {
        Booking undone = undoStack.pop();
        if (undone != null) {
            refundQueue.enqueue(undone);
            System.out.println("✅ UNDO: " + undone.bookingId + " → Refund queue");
            return true;
        }
        System.out.println("❌ Nothing to undo");
        return false;
    }

    // GETTERS
    public static ArrayList<User> getUsers() { return users; }
    public static ArrayList<Booking> getBookings() { return bookings; }
    public static ArrayList<Route> getRoutes() { return routes; }
    public static BookingHistoryList getHistory() { return history; }
    public static BookingQueue getPaymentQueue() { return paymentQueue; }
    public static UndoStack getUndoStack() { return undoStack; }
    public static RefundQueue getRefundQueue() { return refundQueue; }
}
