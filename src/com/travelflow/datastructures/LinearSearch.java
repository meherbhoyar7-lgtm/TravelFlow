package com.travelflow.datastructures;

import com.travelflow.models.Route;
import java.util.ArrayList;

public class LinearSearch {
    public static ArrayList<Route> searchRoutes(ArrayList<Route> routes,
                                                String source, String destination) {
        ArrayList<Route> matches = new ArrayList<>();
        long start = System.nanoTime();

        for (Route route : routes) {
            if (route.source.equalsIgnoreCase(source) &&
                    route.destination.equalsIgnoreCase(destination)) {
                matches.add(route);
            }
        }

        long end = System.nanoTime();
        System.out.printf("Linear Search: %d ns, Found: %d routes%n",
                (end-start), matches.size());
        return matches;
    }
}
