package com.storefront.utilities;

import com.storefront.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SampleData {

    public static List<Customer> createSampleCustomers() {

        List<Customer> customerList = new ArrayList<>();

        // New Customer 1
        Name name = new Name();
        name.setTitle("Mr.");
        name.setFirstName("John");
        name.setMiddleName("S.");
        name.setLastName("Doe");
        name.setSuffix("Jr.");

        Contact contact = new Contact();
        contact.setPrimaryPhone("555-666-7777");
        contact.setSecondaryPhone("555-444-9898");
        contact.setEmail("john.doe@internet.com");

        List<Address> addressList = new ArrayList<>();

        Address address = new Address();
        address.setType(AddressType.BILLING);
        address.setDescription("My cc billing address");
        address.setAddress1("123 Oak Street");
        address.setCity("Sunrise");
        address.setState("CA");
        address.setPostalCode("12345-6789");

        addressList.add(address);

        address = new Address();
        address.setType(AddressType.SHIPPING);
        address.setDescription("My home address");
        address.setAddress1("123 Oak Street");
        address.setCity("Sunrise");
        address.setState("CA");
        address.setPostalCode("12345-6789");

        addressList.add(address);

        List<CreditCard> creditCardList = new ArrayList<>();

        CreditCard creditCard = new CreditCard();
        creditCard.setType(CreditCardType.PRIMARY);
        creditCard.setDescription("VISA");
        creditCard.setNumber("1234-6789-0000-0000");
        creditCard.setExpiration("6/19");
        creditCard.setNameOnCard("John S. Doe");

        creditCardList.add(creditCard);

        creditCard = new CreditCard();
        creditCard.setType(CreditCardType.ALTERNATE);
        creditCard.setDescription("Corporate American Express");
        creditCard.setNumber("9999-8888-7777-6666");
        creditCard.setExpiration("3/20");
        creditCard.setNameOnCard("John Doe");

        creditCardList.add(creditCard);

        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setContact(contact);
        newCustomer.setAddresses(addressList);
        newCustomer.setCreditCards(creditCardList);

        customerList.add(newCustomer);

        // New Customer 2
        name = new Name();
        name.setTitle("Ms.");
        name.setFirstName("Mary");
        name.setLastName("Smith");

        contact = new Contact();
        contact.setPrimaryPhone("456-789-0001");
        contact.setSecondaryPhone("456-222-1111");
        contact.setEmail("marysmith@yougotmail.com");

        addressList = new ArrayList<>();

        address = new Address();
        address.setType(AddressType.BILLING);
        address.setDescription("My CC billing address");
        address.setAddress1("1234 Main Street");
        address.setCity("Anywhere");
        address.setState("NY");
        address.setPostalCode("45455-66677");

        addressList.add(address);

        address = new Address();
        address.setType(AddressType.SHIPPING);
        address.setDescription("Home Sweet Home");
        address.setAddress1("1234 Main Street");
        address.setCity("Anywhere");
        address.setState("NY");
        address.setPostalCode("45455-66677");

        addressList.add(address);

        creditCardList = new ArrayList<>();

        creditCard = new CreditCard();
        creditCard.setType(CreditCardType.PRIMARY);
        creditCard.setDescription("VISA");
        creditCard.setNumber("4545-6767-8989-0000");
        creditCard.setExpiration("7/21");
        creditCard.setNameOnCard("Mary Smith");

        creditCardList.add(creditCard);

        newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setContact(contact);
        newCustomer.setAddresses(addressList);
        newCustomer.setCreditCards(creditCardList);

        customerList.add(newCustomer);

        // New Customer 3
        name = new Name();
        name.setTitle("Ms.");
        name.setFirstName("Susan");
        name.setLastName("Blackstone");

        contact = new Contact();
        contact.setPrimaryPhone("433-544-6555");
        contact.setSecondaryPhone("223-445-6767");
        contact.setEmail("susan.m.blackstone@emailisus.com");

        addressList = new ArrayList<>();

        address = new Address();
        address.setType(AddressType.BILLING);
        address.setDescription("My CC billing address");
        address.setAddress1("33 Oak Avenue");
        address.setCity("Nowhere");
        address.setState("VT");
        address.setPostalCode("444556-9090");

        addressList.add(address);

        address = new Address();
        address.setType(AddressType.SHIPPING);
        address.setDescription("Home Sweet Home");
        address.setAddress1("33 Oak Avenue");
        address.setCity("Nowhere");
        address.setState("VT");
        address.setPostalCode("444556-9090");

        addressList.add(address);

        creditCardList = new ArrayList<>();

        creditCard = new CreditCard();
        creditCard.setType(CreditCardType.PRIMARY);
        creditCard.setDescription("Master Card");
        creditCard.setNumber("4545-5656-7878-9090");
        creditCard.setExpiration("4/19");
        creditCard.setNameOnCard("Susan M. Blackstone");

        creditCardList.add(creditCard);

        newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setContact(contact);
        newCustomer.setAddresses(addressList);
        newCustomer.setCreditCards(creditCardList);

        customerList.add(newCustomer);
        return customerList;
    }

}
