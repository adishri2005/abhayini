// Part 2 (for model pkg): User Entity

// src/main/java/com/abhayini/model/User.java
package com.abhayini.model;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // password will be stored as a hash

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
