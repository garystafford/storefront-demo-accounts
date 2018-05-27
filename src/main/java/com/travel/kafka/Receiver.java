package com.travel.kafka;

import com.travel.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${spring.kafka.topic.json}")
    public void receive(Customer candidate) {
        log.info("received candidate='{}'", candidate.toString());
        latch.countDown();
    }
}