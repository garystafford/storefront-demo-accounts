package com.storefront.controller;

import com.storefront.model.Customer;
import com.storefront.respository.CustomerRepository;
import com.storefront.utilities.SampleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    @RequestMapping(path = "/sample", method = RequestMethod.GET)
    public ResponseEntity<String> sampleData() {

        customerRepository.deleteAll();
        customerRepository.saveAll(SampleData.createSampleCustomers());

        return new ResponseEntity<>("Sample customer accounts created", HttpStatus.CREATED);
    }

    @RequestMapping(path = "/summary", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, List<Customer>>> customerSummary() {

        List<Customer> customerList = customerRepository.findAll();
        return new ResponseEntity<>(Collections.singletonMap("customers", customerList), HttpStatus.OK);
    }
}
