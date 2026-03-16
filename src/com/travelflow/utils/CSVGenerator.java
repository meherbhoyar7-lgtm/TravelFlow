package com.travelflow.utils;

import com.travelflow.models.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CSVGenerator {
    private static final String LOG_PATH = "reports/TravelFlow_AutoLog.csv";
    private static PrintWriter logWriter;

    static {
        try {
            new File("reports").mkdirs();
            new File("reports/TravelFlow_Report.csv").delete();
            new File("reports/TravelFlow_AutoLog.xlsx").delete();

            logWriter = new PrintWriter(new FileWriter(LOG_PATH, true));
            File logFile = new File(LOG_PATH);
            if (logFile.length() == 0) {
                logWriter.println("Timestamp,Action,UserID,BookingID,RouteCode,Seats,Amount,Status");
            }
            logWriter.flush();
        } catch (IOException e) {
            System.err.println("Auto-log init failed: " + e.getMessage());
        }
    }

    public static synchronized void logAction(String action, String userId, String bookingId,
                                              String routeCode, int seats, double amount, String status) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logWriter.println(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d,%.0f,\"%s\"",
                    timestamp, action, userId, bookingId, routeCode, seats, amount, status));
            logWriter.flush();
        } catch (Exception e) {
            System.err.println("Log failed: " + e.getMessage());
        }
    }
}
