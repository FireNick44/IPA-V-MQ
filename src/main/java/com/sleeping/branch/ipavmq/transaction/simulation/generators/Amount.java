package com.sleeping.branch.ipavmq.transaction.simulation.generators;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Amount {
    private final Random random = new Random();

    public double getRandomAmount() {
        double randomAmount = generateRandomAmount();
        return convertRandomAmount(randomAmount);
    }

    private double generateRandomAmount() {
        double randomNumber = random.nextDouble();
        if (randomNumber <= 0.2) {
            return random.nextInt(10000000) + 1; // 20%
        } else if (randomNumber <= 0.5) {
            return random.nextInt(1000) + 1; // 30%
        } else {
            return random.nextInt(100) + 1; // 50%
        }
    }

    private double convertRandomAmount(double amountToConvert) {
        double randomNumber = random.nextDouble();
        if (randomNumber <= 0.3) {
            return convertAmountWithOneDigit(amountToConvert); // 30%
        } else if (randomNumber <= 0.6) {
            return convertAmountWithPointZero(amountToConvert); // 30%
        } else {
            return convertAmountWithRandomDigits(amountToConvert); //40%
        }
    }

    private double convertAmountWithOneDigit(double amountToConvert) {
        double pow = Math.pow(10, Math.floor(Math.log10(amountToConvert)));
        int firstDigit = (int) (amountToConvert / pow);
        return firstDigit * pow;
    }

    private double convertAmountWithPointZero(double amountToConvert) {
        int lastTwoDigits = (int) (amountToConvert * 100) % 100;
        return (int) (amountToConvert) + (lastTwoDigits / 100.0);
    }

    private double convertAmountWithRandomDigits(double amountToConvert) {
        double multiplier = Math.pow(10, 2);
        return Math.round(amountToConvert * multiplier) / multiplier;
    }
}