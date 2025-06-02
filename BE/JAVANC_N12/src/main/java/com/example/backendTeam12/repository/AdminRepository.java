package com.example.backendTeam12.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backendTeam12.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String admin_username);
    boolean existsByUsername(String admin_username);
}
