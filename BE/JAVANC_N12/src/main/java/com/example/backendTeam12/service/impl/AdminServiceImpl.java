package com.example.backendTeam12.service.impl;

import com.example.backendTeam12.model.Admin;
import com.example.backendTeam12.repository.AdminRepository;
import com.example.backendTeam12.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    @Override
    public void loginAdmin(String username, String password) {
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);
        if (adminOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy admin");
        }
        
        Admin admin = adminOptional.get();
        if (!password.equalsIgnoreCase(admin.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }
    }
}