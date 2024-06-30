package com.example.store_application.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store_application.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
