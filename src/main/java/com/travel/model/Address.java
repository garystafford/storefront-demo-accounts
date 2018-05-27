package com.travel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @NonNull
    private AddressType type;

    @NonNull
    private String description;

    @NonNull
    private String address1;

    private String address2;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private String postalCode;

}
