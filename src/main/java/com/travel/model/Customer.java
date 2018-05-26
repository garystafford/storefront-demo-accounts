package com.travel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Customer {

    @Id
    private ObjectId id;

    @NonNull
    private Name name;

    @NonNull
    private Contact contact;

    @NonNull
    private List<Address> addresses;

    @NonNull
    private List<CreditCard> creditCards;

    @NonNull
    private Credentials credentials;

}
