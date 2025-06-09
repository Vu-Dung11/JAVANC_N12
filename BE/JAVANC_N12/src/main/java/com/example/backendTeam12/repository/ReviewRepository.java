package com.example.backendTeam12.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findByHomestayHomestayId(Long homestayId);
    public List<Review> findByUserUserId(Long userId);
    public List<Review> findByRate(Integer rate);

    @Query("SELECT DISTINCT r.homestay from Review r WHERE r.rate >= 3")
    List<Homestay> getGHomestaysByHighRate();

    @Query("select count(r) from Review r where r.rate >= 3")
    long getTotalGoodReviewRate();

    @Query("select count(r) from Review r ")
    long getTotalReviewRate();

}
