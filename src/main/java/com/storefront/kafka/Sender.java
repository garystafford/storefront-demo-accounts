package com.storefront.kafka;

import com.storefront.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class Sender {

    @Autowired
    private KafkaTemplate<String, Customer> kafkaTemplate;

    public void send(String topic, Customer payload) {

        log.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}