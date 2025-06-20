package com.example.backendTeam12.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.Admin;
import com.example.backendTeam12.repository.AdminRepository;
import com.example.backendTeam12.service.AdminService;

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
        try{
            Optional<Admin> adminOptional = adminRepository.findByUsername(username);
            if (adminOptional.isEmpty()) {
                throw new RuntimeException("Không tìm thấy admin");
            }
            
            Admin admin = adminOptional.get();
            if (!password.equalsIgnoreCase(admin.getPassword())) {
                throw new RuntimeException("Mật khẩu không đúng");
            }
        }catch(DataAccessException e){
            System.err.println("Lỗi CSDL (DataAccessException): " + e.getMessage());
            throw new RuntimeException("Lỗi khi truy cập cơ sở dữ liệu");
        }
    }
}