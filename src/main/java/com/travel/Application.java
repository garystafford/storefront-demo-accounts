package com.travel;

import com.travel.model.Address;
import com.travel.model.CreditCard;
import com.travel.model.Customer;
import com.travel.respository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {

        customerRepository.deleteAll();

        // New Customer 1
        Address address1 = new Address();
        address1.setAddress1("123 Oak Street");
        address1.setCity("Sunrise");
        address1.setState("CA");
        address1.setPostalCode("12345-6789");

        CreditCard creditCard1 = new CreditCard();
        creditCard1.setNumber("1234-6789-0000-0000");
        creditCard1.setExpiration("6/19");
        creditCard1.setNameOnCard("John S. Doe");

        Customer newCustomer1 = new Customer();
        newCustomer1.setFirstName("John");
        newCustomer1.setLastName("Doe");
        newCustomer1.setPrimaryPhone("555-666-7777");
        newCustomer1.setEmail("john.doe@internet.com");
        newCustomer1.setBillingAddress(address1);
        newCustomer1.setShippingAddress(address1);
        newCustomer1.setPrimaryCreditCard(creditCard1);
        newCustomer1.setUsername("johndoe37");
        newCustomer1.setPassword("skd837#$hfh485&");

        // New Customer 2
        Address address2 = new Address();
        address2.setAddress1("1234 Main Street");
        address2.setCity("Anywhere");
        address2.setState("NY");
        address2.setPostalCode("45455-66677");

        CreditCard creditCard2 = new CreditCard();
        creditCard2.setNumber("4545-6767-8989-0000");
        creditCard2.setExpiration("7/21");
        creditCard2.setNameOnCard("Mary Smith");

        Customer newCustomer2 = new Customer();
        newCustomer2.setFirstName("Mary");
        newCustomer2.setLastName("Smith");
        newCustomer2.setPrimaryPhone("456-789-0001");
        newCustomer2.setEmail("marysmith@yougotmail.com");
        newCustomer2.setBillingAddress(address2);
        newCustomer2.setShippingAddress(address2);
        newCustomer2.setPrimaryCreditCard(creditCard2);
        newCustomer2.setUsername("msmith445");
        newCustomer2.setPassword("S*$475hf&*dddFFG3");

        customerRepository.save(newCustomer1);
        customerRepository.save(newCustomer2);

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        System.out.println("Customers found with findByLastName('Doe'):");
        System.out.println("--------------------------------");
        for (Customer customer : customerRepository.findByLastName("Doe")) {
            System.out.println(customer);
        }
    }

}
