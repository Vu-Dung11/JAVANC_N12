package com.example.backendTeam12.service;

import java.util.List;
import java.util.Optional;

import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.model.Review;

public interface ReviewService {
    Review createReview(Review review);
    Review updateReview(Long id, Review review);
    void deleteReview(Long id);
    Optional<Review> getReviewById(Long id);
    List<Review> getAllReviews();
    List<Review> getReviewsByHomestayHomestayId(Long homestayId);
    List<Review> getReviewsByUserUserId(Long userId);
    List<Review> getReviewsByRate(Integer rate);

    double getHomestayWithAverageRate(Long homestayId);
    List<Homestay> getHomestaysByHighRate(); 
    List<Review> getReviewsByKeyword(String keyword);

    int percentGoodHomestay();
    int percentNotGoodHomestay();
}
