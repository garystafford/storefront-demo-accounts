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
public class Contact {

    @Id
    private ObjectId id;

    @NonNull
    private String primaryPhone;

    private String secondaryPhone;

    @NonNull
    private String email;

}
