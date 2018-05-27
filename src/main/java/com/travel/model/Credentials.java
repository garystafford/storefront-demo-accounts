package com.travel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

    @NonNull
    private String username;

    @NonNull
    private String password;

}
