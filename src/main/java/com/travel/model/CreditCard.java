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
public class CreditCard {

    @Id
    private ObjectId id;

    @NonNull
    private CreditCardType type;

    @NonNull
    private String description;

    @NonNull
    private String number;

    @NonNull
    private String expiration;

    @NonNull
    private String nameOnCard;

}
