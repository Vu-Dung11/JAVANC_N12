package com.example.backendTeam12.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.User;
import com.example.backendTeam12.repository.UserRepository;
import com.example.backendTeam12.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        
        //update
        existingUser.setUserName(user.getUserName());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setGender(user.getGender());
        existingUser.setFullName(user.getFullName());
        existingUser.setAvatar(user.getAvatar());
        existingUser.setUpdatedAt(LocalDateTime.now());

        //save
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUserName(String username) {
        return Optional.ofNullable(userRepository.findByUserName(username));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void loginUser(String username, String password){
         Optional<User> optionalUser = getUserByUserName(username);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
    @Override
    public List<User> searchUsersByUsername(String search) {
        return userRepository.findByUserNameContainingIgnoreCase(search);
    }

    @Override
    public int percentNewUser(){
        LocalDate  now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        long totalUsers = userRepository.countAllUser();
        if (totalUsers == 0){
            return 0;
        }

        long newUsers = userRepository.countAllUserCreatedAtMonth(month, year);
        if(newUsers == 0){
            return 0;
        }

        int percent = (int) (((double)newUsers / totalUsers) * 100);
       
        return  percent;
    }
} 