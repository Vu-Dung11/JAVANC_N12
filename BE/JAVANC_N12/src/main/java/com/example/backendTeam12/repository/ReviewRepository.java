package com.example.backendTeam12.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backendTeam12.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findByHomestayHomestayId(Long homestayId);
    public List<Review> findByUserUserId(Long userId);
    public List<Review> findByRate(Integer rate);
}
