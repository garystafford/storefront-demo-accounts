package com.travel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Customer {

    @Id
    private ObjectId id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String primaryPhone;

    private String secondaryPhone;

    @NonNull
    private String email;

    @NonNull
    private Address billingAddress;

    @NonNull
    private Address shippingAddress;

    @NonNull
    private CreditCard primaryCreditCard;

    private CreditCard secondaryCreditCard;

    @NonNull
    private String username;

    @NonNull
    private String password;

}
