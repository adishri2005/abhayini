// Part 1 (for repository pkg):
//the database integration is planned for later,
//we have created a repository interface using Spring Data JPA.

// src/main/java/com/abhayini/repository/UserRepository.java
package com.abhayini.repository;

import com.abhayini.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);
}
