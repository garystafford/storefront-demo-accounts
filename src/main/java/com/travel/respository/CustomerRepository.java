package com.travel.respository;

import com.travel.model.Customer;
import com.travel.model.Name;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {

//    List<Customer> findByName(Name name);
}