package com.sleeping.branch.ipavmq.transaction.simulation.calculate;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ProcessingTime {
    public Long calculateProcessingTime(String senderServerLocation, String receiverServerLocation, Long memoryUsage, LocalDateTime timestamp) {
        ZonedDateTime senderLocalTime = timestamp.atZone(ZoneId.of(senderServerLocation));
        ZonedDateTime receiverLocalTime = timestamp.atZone(ZoneId.of(receiverServerLocation));
        long baseProcessingTime = 100L;

        if (!senderLocalTime.equals(receiverLocalTime)) {
            long timeDifference = Math.abs(senderLocalTime.toInstant().toEpochMilli() - receiverLocalTime.toInstant().toEpochMilli());;
            if (timeDifference > 9 * 60 * 60 * 1000) {
                baseProcessingTime += 1000L; // 9 hours or more, add 1 seconds
            } else if (timeDifference > 6 * 60 * 60 * 1000) {
                baseProcessingTime += 600L; // 6 hours or more, add 0.6 seconds
            } else if (timeDifference > 3 * 60 * 60 * 1000) {
                baseProcessingTime += 300L; // 3 hours or more, add 0.3 seconds
            } else {
                baseProcessingTime += 100L; // 1 hour or more, add 0.1 seconds
            }
            baseProcessingTime += memoryUsage;
        }
        return baseProcessingTime;
    }
}