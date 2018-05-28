package com.storefront.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Name {

    @NonNull
    private String title;

    @NonNull
    private String firstName;

    private String middleName;

    @NonNull
    private String lastName;

    private String suffix;

}
