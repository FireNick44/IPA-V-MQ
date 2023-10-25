package com.sleeping.branch.ipavmq.transaction.simulation;

import com.sleeping.branch.ipavmq.transaction.Transaction;
import com.sleeping.branch.ipavmq.transaction.simulation.calculate.MemoryUsage;
import com.sleeping.branch.ipavmq.transaction.simulation.calculate.ProcessingTime;
import com.sleeping.branch.ipavmq.transaction.simulation.generators.AccountNumber;
import com.sleeping.branch.ipavmq.transaction.simulation.generators.Amount;
import com.sleeping.branch.ipavmq.transaction.simulation.generators.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SimulationMatrix {

    private final Amount amount;
    private final AccountNumber accountNumber;
    private final Location location;
    private final MemoryUsage memoryUsage;
    private final ProcessingTime processingTime;

    @Autowired
    public SimulationMatrix(
            Amount amount,
            AccountNumber accountNumber,
            Location location,
            MemoryUsage memoryUsage,
            ProcessingTime processingTime
    ) {
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.location = location;
        this.memoryUsage = memoryUsage;
        this.processingTime = processingTime;
    }

    public Transaction createSimulatedTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTimestamp(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS));
        transaction.setAmount(amount.getRandomAmount());
        transaction.setSenderServerLocation(location.getRandomLocation());
        transaction.setReceiverServerLocation(location.getRandomLocation());
        transaction.setMemoryUsage(memoryUsage.calculateMemoryUsage(
                transaction.getTimestamp(),
                transaction.getAmount(),
                transaction.getSenderServerLocation()
        ));
        transaction.setProcessingTime(processingTime.calculateProcessingTime(
                transaction.getSenderServerLocation(),
                transaction.getReceiverServerLocation(),
                transaction.getMemoryUsage(),
                transaction.getTimestamp()
        ));
        transaction.setSenderAccountNumber(accountNumber.getRandomAccountNumber());
        transaction.setReceiverAccountNumber(accountNumber.getRandomAccountNumber());
        return transaction;
    }
}
