package com.sleeping.branch.ipavmq.transaction.simulation.generators;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

@Component
public class Location {
    private final Random random = new Random();

    @Getter
    public enum ServerLocations {
        AFRICA("Africa/Nairobi", 0),
        ASIA("Asia/Singapore", 8),
        EUROPE("Europe/Zurich", 1),
        EAST_AMERICA("America/New_York", -5),
        WEST_AMERICA("America/Los_Angeles", -3),
        AUSTRALIA("Australia/Sydney", 10);

        private final String timeZone;
        private final int timeDifference;

        ServerLocations(String timeZone, int timeDifference) {
            this.timeZone = timeZone;
            this.timeDifference = timeDifference;
        }
    }

    public String getRandomLocation() {
        ServerLocations[] locations = ServerLocations.values();
        ServerLocations closestLocation = findClosestLocationTo1PM(locations);
        double[] weights = calculateLocationWeights(locations, closestLocation);

        double randomValue = random.nextDouble();
        return selectLocationByWeights(locations, weights, randomValue);
    }

    private ServerLocations findClosestLocationTo1PM(ServerLocations[] locations) {
        LocalTime currentTime = LocalTime.now();
        ServerLocations closestLocation = null;
        long closestTimeDifference = Long.MAX_VALUE;

        for (ServerLocations location : locations) {
            ZoneId zoneId = ZoneId.of(location.getTimeZone());
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
            LocalTime localTime = zonedDateTime.toLocalTime();
            long timeDifference = Math.abs(currentTime.toSecondOfDay() - localTime.toSecondOfDay());

            if (timeDifference < closestTimeDifference) {
                closestTimeDifference = timeDifference;
                closestLocation = location;
            }
        }
        return closestLocation;
    }

    private double[] calculateLocationWeights(ServerLocations[] locations, ServerLocations closestLocation) {
        double[] weights = new double[locations.length];
        double totalWeight = 0.0;

        for (int i = 0; i < locations.length; i++) {
            if (locations[i] == closestLocation) {
                weights[i] = 0.3; // Closest to 1pm (30%)
            } else {
                weights[i] = 0.7 / (locations.length - 1); // Equal chance for others
            }

            totalWeight += weights[i];
        }

        return weights;
    }

    private String selectLocationByWeights(ServerLocations[] locations, double[] weights, double randomValue) {
        double cumulativeWeight = 0.0;
        for (int i = 0; i < locations.length; i++) {
            cumulativeWeight += weights[i];
            if (randomValue < cumulativeWeight) {
                return locations[i].getTimeZone();
            }
        }
        return locations[0].getTimeZone();
    }
}