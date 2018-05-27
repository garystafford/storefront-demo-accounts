package com.travel.kafka;

import com.travel.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class Sender {

    @Value("${spring.kafka.topic.json}")
    private String jsonTopic;

    @Autowired
    private KafkaTemplate<String, Customer> kafkaTemplate;

    public void send(Customer payload) {
        log.info("sending payload='{}' to topic='{}'", payload, jsonTopic);
        kafkaTemplate.send(jsonTopic, payload);
    }
}