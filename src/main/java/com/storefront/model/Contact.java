package com.storefront.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @NonNull
    private String primaryPhone;

    private String secondaryPhone;

    @NonNull
    private String email;

}
