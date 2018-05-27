package com.travel.handler;

import com.travel.kafka.Sender;
import com.travel.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Controller;

@Controller
public class BeforeSaveListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private Sender sender;

    @Override
    public void onBeforeSave(BeforeSaveEvent<Object> event) {
        System.out.print("I found this: " + event.getSource());
        System.out.print("I found this: " + event.getDocument());
//        Customer customer = event.getSource();
//        sender.send(customer);

    }
}