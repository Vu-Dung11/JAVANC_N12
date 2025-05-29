package com.example.backendTeam12.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backendTeam12.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    public Optional<User> findByEmail(String email);
}