package com.sleeping.branch.ipavmq.transaction.simulation.generators;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountNumber {
    private final Random random = new Random();
    private final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String getRandomAccountNumber() {
        StringBuilder accountNumberBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int letterIndex = random.nextInt(letters.length());
            char randomLetter = letters.charAt(letterIndex);
            accountNumberBuilder.append(randomLetter);
        }
        for (int i = 0; i < 8; i++) {
            int randomDigit = random.nextInt(9) + 1;
            accountNumberBuilder.append(randomDigit);
        }

        return accountNumberBuilder.toString();
    }
}