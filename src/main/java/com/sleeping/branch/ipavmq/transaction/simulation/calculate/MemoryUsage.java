package com.sleeping.branch.ipavmq.transaction.simulation.calculate;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

@Component
public class MemoryUsage {
    public Long calculateMemoryUsage(LocalDateTime timestamp, Double amount, String senderServerLocation) {
        Long baseMemoryUsage = 128L;
        baseMemoryUsage += increaseMemoryBasedOnDeviation();
        baseMemoryUsage += increaseMemoryBasedOnAmount(amount);
        baseMemoryUsage += increaseMemoryBasedOnHour(timestamp, senderServerLocation);
        return baseMemoryUsage;
    }

    private static Long increaseMemoryBasedOnDeviation() {
        Random random = new Random();
        return (random.nextBoolean() ? 1 : -1) * random.nextLong() % 129L;
    }

    private static Long increaseMemoryBasedOnAmount(Double amount) {
        if (amount <= 10) {
            return 256L;
        } else if (amount <= 100) {
            return 512L;
        } else if (amount <= 1000) {
            return 1024L;
        } else {
            return 2048L;
        }
    }

    private static Long increaseMemoryBasedOnHour(LocalDateTime timestamp, String senderServerLocation) {
        ZonedDateTime localTime = timestamp.atZone(ZoneId.of(senderServerLocation));
        int hour = localTime.getHour();

        if (hour >= 1 && hour <= 7) {
            return 256L;
        } else if (hour >= 8 && hour <= 12) {
            return 512L;
        } else if (hour >= 13 && hour <= 18) {
            return 1024L;
        } else {
            return 256L;
        }
    }
}