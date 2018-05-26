package com.travel.kafka;

import com.travel.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Sender.class);

    @Value("${spring.kafka.topic.json}")
    private String jsonTopic;

    @Autowired
    private KafkaTemplate<String, Customer> kafkaTemplate;

    public void send(Customer payload) {
        LOGGER.info("sending payload='{}' to topic='{}'", payload, jsonTopic);
        kafkaTemplate.send(jsonTopic, payload);
    }
}