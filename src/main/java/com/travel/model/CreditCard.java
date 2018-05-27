package com.travel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

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
