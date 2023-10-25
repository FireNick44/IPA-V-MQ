package com.sleeping.branch.ipavmq.transaction;

import com.sleeping.branch.ipavmq.rabbitmq.RabbitMQProducer;
import com.sleeping.branch.ipavmq.transaction.simulation.SimulationMatrix;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Slf4j
@Service
public class TransactionService {
    private final RabbitMQProducer rabbitMQProducer;
    private final SimulationMatrix simulationMatrix;

    @Autowired
    public TransactionService(RabbitMQProducer rabbitMQProducer, SimulationMatrix simulationMatrix) {
        this.rabbitMQProducer = rabbitMQProducer;
        this.simulationMatrix = simulationMatrix;
    }

    @Scheduled(fixedRate = 1000) // Send every second
    public void sendScheduledTransactionToQueue() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Transaction transaction = createTransaction();
        stopWatch.stop();
        log.debug("Generated transaction in: {} ns", stopWatch.getTotalTimeNanos());

        sendTransactionToQueue(transaction);
    }

    private Transaction createTransaction() {
        return simulationMatrix.createSimulatedTransaction();
    }

    public void sendTransactionToQueue(Transaction transaction) {
        ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());
        String jsonTransaction = "500";

        try {
            jsonTransaction = objectMapper.writeValueAsString(transaction);
        } catch (JsonProcessingException message) {
            log.error("JSON Parsing complication -> %s", message);
        }
        rabbitMQProducer.sendMessage(jsonTransaction);
    }
}
