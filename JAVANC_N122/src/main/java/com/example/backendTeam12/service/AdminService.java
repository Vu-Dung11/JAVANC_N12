package com.example.backendTeam12.service;

import java.util.List;
import java.util.Optional;

import com.example.backendTeam12.model.Admin;

public interface AdminService {
    Admin createAdmin(Admin admin);
    Optional<Admin> getAdminById(Long id);
    Optional<Admin> getAdminByUsername(String username);
    List<Admin> getAllAdmins();
    boolean existsByUsername(String username);
    void loginAdmin(String username, String password);
}