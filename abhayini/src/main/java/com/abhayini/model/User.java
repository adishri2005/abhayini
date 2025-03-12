// Part 2 (for model pkg): User Entity

// src/main/java/com/abhayini/model/User.java
package com.abhayini.model;

import jakarta.persistence.*;
import lombok.*;

@Entity                       // specifies that the class is an entity and is mapped to a database table
@Getter                       // lombok annotation to generate getter methods for all fields
@Setter                       // lombok- generate setter methods-
@NoArgsConstructor            // lombok- generate- no-argument constructor
@AllArgsConstructor           // lombok- generate- constructor with all fields as parameters
@Table(name = "users")        // specifies the name of the db table to be used for mapping
public class User
{
    @Id                                                   // primary key of entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // provides the specification of generation strategies for the values of primary keys
    private Long id;                                      // unique identifier for the user

    @Column(unique = true, nullable = false)              // specifies the mapped column for a persistent property or field
    private String email;                                 // email of the user, must be unique and not null

    @Column(nullable = false)
    private String username;                               // username of the user, must not be null

    @Column(nullable = false)
    private String password;                               // password of the user, stored as a hash, must not be null

    @Enumerated(EnumType.STRING) // specifies that the persistent property or field should be persisted as an enumerated type
    private UserRole role;                          // role of the user, represented as an enum
}