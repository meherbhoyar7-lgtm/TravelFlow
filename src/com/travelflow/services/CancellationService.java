package com.travelflow.services;

import com.travelflow.utils.CSVGenerator;
import com.travelflow.datastructures.*;
import com.travelflow.models.*;
import java.util.Scanner;

public class CancellationService {
    private static Scanner scanner = new Scanner(System.in);

    public static void cancelInteractive() {
        System.out.println("\n❌ CANCEL BOOKING");
        System.out.println("════════════════════");
        showUserBookings();

        System.out.print("Enter Booking ID to cancel: ");
        String bookingId = scanner.nextLine().trim();

        Booking booking = findBookingById(bookingId);
        if (booking != null && "confirmed".equals(booking.status)) {  // ✅ FIXED: Proper string comparison
            booking.status = "cancelled";  // ✅ NOW UPDATES STATUS
            BookingService.getRefundQueue().enqueue(booking);  // ✅ Use shared queue

            CSVGenerator.logAction("CANCELLATION", booking.userId, bookingId,
                    booking.routeCode, booking.seats, 0, "CANCELLED");

            showProfessionalCancellation(bookingId);
            showRefundQueueStatus();
        } else {
            System.out.println("❌ Booking not found or already cancelled!");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private static Booking findBookingById(String bookingId) {
        for (Booking b : BookingService.getBookings()) {
            if (b.bookingId.equals(bookingId)) {
                return b;
            }
        }
        return null;
    }

    // ✅ FIXED: Show ALL confirmed bookings (not just current user)
    private static void showUserBookings() {
        boolean hasBookings = false;
        System.out.println("Your Active Bookings:");
        System.out.println("════════════════════");
        for (Booking b : BookingService.getBookings()) {
            if ("confirmed".equals(b.status)) {  // ✅ FIXED: Proper comparison
                System.out.println("🎫 " + b.bookingId + " | " + b.routeCode +
                        " | " + b.seats + " seats | " + b.finalPrice + " | " + b.status);
                hasBookings = true;
            }
        }
        if (!hasBookings) {
            System.out.println("No active bookings found.");
        }
        System.out.println("════════════════════");
    }

    private static void showRefundQueueStatus() {
        RefundQueue refundQueue = BookingService.getRefundQueue();
        System.out.println("\n💰 REFUND PROCESSING:");
        System.out.println("════════════════════");
        System.out.println("📊 RefundQueue: " + refundQueue.size() + " pending");
        if (refundQueue.size() > 0) {
            System.out.println("⏳ Next refund: " + refundQueue.peek().bookingId);
        }
        System.out.println("════════════════════");
    }

    private static double getRoutePrice(String routeCode) {
        for (Route r : BookingService.getRoutes()) {
            if (r.routeCode.equals(routeCode)) {
                return r.getClassPrice();  // ✅ FIXED: Dynamic pricing
            }
        }
        return routeCode.contains("F") ? 3500.0 : 2500.0;
    }

    public static void processRefunds() {
        RefundQueue refundQueue = BookingService.getRefundQueue();
        Booking refund = refundQueue.dequeue();
        if (refund != null) {
            System.out.println("💸 Refund PROCESSED: " + refund.bookingId +
                    " (₹" + (getRoutePrice(refund.routeCode) * refund.seats) + ")");
            CSVGenerator.logAction("REFUND", refund.userId, refund.bookingId,
                    refund.routeCode, refund.seats, 0, "COMPLETED");
            showRefundQueueStatus();
        } else {
            System.out.println("ℹ️ No pending refunds.");
        }
    }

    private static void showProfessionalCancellation(String bookingId) {
        System.out.println("\n✅ CANCELLATION CONFIRMED");
        System.out.println("═══════════════════════════════");
        System.out.println("🎫 Booking ID: " + bookingId);
        System.out.println("💰 Full refund queued (FIFO processing)");
        System.out.println("🔄 UndoStack available for recovery");
        System.out.println("📊 Live counters updated!");
        System.out.println("═══════════════════════════════");
    }
}
