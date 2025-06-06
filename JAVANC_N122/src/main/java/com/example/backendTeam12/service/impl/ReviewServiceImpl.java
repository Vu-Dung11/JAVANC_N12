package com.example.backendTeam12.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.Review;
import com.example.backendTeam12.repository.ReviewRepository;
import com.example.backendTeam12.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long id, Review review) {
         Review existingReview = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));

        existingReview.setRate(review.getRate());
        existingReview.setComment(review.getComment());
        existingReview.setHomestay(review.getHomestay());
        existingReview.setUser(review.getUser());

        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsByHomestayHomestayId(Long homestayId) {
        return reviewRepository.findByHomestayHomestayId(homestayId);
    }

    @Override
    public List<Review> getReviewsByUserUserId(Long userId) {
        return reviewRepository.findByUserUserId(userId);
    }

    @Override
    public List<Review> getReviewsByRate(Integer rate) {
        return reviewRepository.findByRate(rate);
    }
}
