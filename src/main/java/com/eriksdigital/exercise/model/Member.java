package com.eriksdigital.exercise.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Document(collection = "user")
public class Member {

    @Id
    private long id;

    private String username;

    private String password;


}
