package com.travel.respository;

import com.travel.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

//    List<Customer> findByName(Name name);
}