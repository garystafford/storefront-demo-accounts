package com.storefront.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    @NonNull
    private Name name;

    @NonNull
    private Contact contact;

    @NonNull
    private List<Address> addresses;

    @NonNull
    private List<CreditCard> creditCards;
}
