package com.travel.handler;

import com.travel.kafka.Sender;
import com.travel.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Controller;

@Controller
public class BeforeConvertListener extends AbstractMongoEventListener<Customer> {

    @Autowired
    private Sender sender;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Customer> event) {
        System.out.print("I found this: " + event.getSource());
        Customer customer = event.getSource();
        sender.send(customer);

    }
}