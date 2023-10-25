package com.sleeping.branch.ipavmq;

import com.sleeping.branch.ipavmq.transaction.Transaction;
import com.sleeping.branch.ipavmq.transaction.simulation.SimulationMatrix;
import com.sleeping.branch.ipavmq.transaction.simulation.calculate.MemoryUsage;
import com.sleeping.branch.ipavmq.transaction.simulation.calculate.ProcessingTime;
import com.sleeping.branch.ipavmq.transaction.simulation.generators.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class simulatedTransactionTest {

    @Autowired
    private SimulationMatrix simulationMatrix;

    @Test
    public void testSimulationMatrixShouldReturnNotNullTransaction() {
        Transaction transaction = simulationMatrix.createSimulatedTransaction();

        assertNotNull(transaction.getTimestamp());
        assertNotNull(transaction.getAmount());
        assertNotNull(transaction.getSenderServerLocation());
        assertNotNull(transaction.getReceiverServerLocation());
        assertNotNull(transaction.getMemoryUsage());
        assertNotNull(transaction.getProcessingTime());
        assertNotNull(transaction.getSenderAccountNumber());
        assertNotNull(transaction.getReceiverAccountNumber());
    }

    @Test
    public void testSimulationMatrixShouldReturnValidTransactionTimestamp() {
        Transaction transaction = simulationMatrix.createSimulatedTransaction();
        LocalDateTime localTimestamp = LocalDateTime.now();

        assertNotNull(transaction);
        assertNotNull(transaction.getTimestamp());
        assertTrue(Math.abs(localTimestamp.getSecond() - transaction.getTimestamp().getSecond()) <= 1);
    }

    @Test
    public void testSimulationMatrixShouldReturnValidTransactionAmount() {
        Transaction transaction = simulationMatrix.createSimulatedTransaction();
        assertTrue(transaction.getAmount() >= 1 && transaction.getAmount() <= 10000000);
    }

    @Test
    public void testSimulationMatrixShouldReturnValidTransactionSenderServerLocation() {
        Transaction transaction = simulationMatrix.createSimulatedTransaction();
        boolean matches = false;
        Location.ServerLocations[] validTimeZones = {
                Location.ServerLocations.AFRICA,
                Location.ServerLocations.ASIA,
                Location.ServerLocations.EUROPE,
                Location.ServerLocations.EAST_AMERICA,
                Location.ServerLocations.WEST_AMERICA,
                Location.ServerLocations.AUSTRALIA
        };

        for (Location.ServerLocations validTimeZone : validTimeZones) {
            if (transaction.getSenderServerLocation().equals(validTimeZone.getTimeZone())) {
                matches = true;
                break;
            }
        }
        assertTrue(matches);
    }

    @Test
    public void testSimulationMatrixShouldReturnValidTransactionReceiverServerLocation() {
        Transaction transaction = simulationMatrix.createSimulatedTransaction();
        boolean matches = false;
        Location.ServerLocations[] validTimeZones = {
                Location.ServerLocations.AFRICA,
                Location.ServerLocations.ASIA,
                Location.ServerLocations.EUROPE,
                Location.ServerLocations.EAST_AMERICA,
                Location.ServerLocations.WEST_AMERICA,
                Location.ServerLocations.AUSTRALIA
        };

        for (Location.ServerLocations validTimeZone : validTimeZones) {
            if (transaction.getReceiverServerLocation().equals(validTimeZone.getTimeZone())) {
                matches = true;
                break;
            }
        }
        assertTrue(matches);
    }

    @Test
    public void testSimulationMatrixShouldReturnValidTransactionMemoryUsage() {
        MemoryUsage memoryUsage = new MemoryUsage();
        Transaction transaction = new Transaction();
        transaction.setMemoryUsage(memoryUsage.calculateMemoryUsage(
                LocalDateTime.of(2023, 10, 25, 13, 0, 0, 0),
                100.0,
                "Europe/Zurich"
        ));

        long actualProcessingTime = transaction.getMemoryUsage();
        long expectedValue = 1664;
        long tolerance = 128L;

        assertTrue(actualProcessingTime >= (expectedValue - tolerance) && actualProcessingTime <= (expectedValue + tolerance));
    }

    @Test
    public void testSimulationMatrixShouldReturnValidTransactionProcessingTime(){
        ProcessingTime processingTime = new ProcessingTime();
        Transaction transaction = new Transaction();
        transaction.setProcessingTime(processingTime.calculateProcessingTime(
                Location.ServerLocations.EUROPE.getTimeZone(),
                Location.ServerLocations.ASIA.getTimeZone(),
                0L,
                LocalDateTime.now()
        ));

        assertEquals(400L, transaction.getProcessingTime());
    }
}
