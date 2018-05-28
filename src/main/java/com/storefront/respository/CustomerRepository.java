package com.storefront.respository;

import com.storefront.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

//    List<Customer> findByName(Name name);
}