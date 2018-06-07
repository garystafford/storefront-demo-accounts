package com.storefront.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CustomerChangeEvent {

    @Id
    private String id;

    @NonNull
    private Name name;

    @NonNull
    private Contact contact;

    @NonNull
    private List<Address> addresses;
}
