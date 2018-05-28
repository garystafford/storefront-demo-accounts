package com.storefront.handler;

import com.storefront.kafka.Sender;
import com.storefront.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Controller;

@Controller
public class AfterSaveListener extends AbstractMongoEventListener<Customer> {

    private Sender sender;

    @Autowired
    public AfterSaveListener(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Customer> event) {
        System.out.print("I found this Source: " + event.getSource());
        Customer customer = event.getSource();
        sender.send(customer);

    }
}