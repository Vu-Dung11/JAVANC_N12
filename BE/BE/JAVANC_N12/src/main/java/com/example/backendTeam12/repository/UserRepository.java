package com.example.backendTeam12.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backendTeam12.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    public Optional<User> findByEmail(String email);
    List<User> findByUserNameContainingIgnoreCase(String search);

    //-> count user
    @Query("SELECT COUNT(u) FROM User u")
    long countAllUser();

    //count user theo month, year
    @Query("SELECT COUNT(u) FROM User u WHERE MONTH(u.createdAt) = :month AND YEAR(u.createdAt) = :year")
    long countAllUserCreatedAtMonth(@Param("month") int month, @Param("year") int year);
}