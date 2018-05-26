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
public class Name {

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    @NonNull
    private String firstName;

    private String middleName;

    @NonNull
    private String lastName;

    private String suffix;

}
