package com.example.backendTeam12.service;

import com.example.backendTeam12.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUserName(String username);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    void loginUser(String username, String password);
}