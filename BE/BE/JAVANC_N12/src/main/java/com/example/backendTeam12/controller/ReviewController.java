package com.example.backendTeam12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.model.Review;
import com.example.backendTeam12.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.createReview(review));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(id, review);
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/homestay/{homestayId}")
    public ResponseEntity<List<Review>> getReviewsByHomestayHomestayId(@PathVariable Long homestayId) {
        return ResponseEntity.ok(reviewService.getReviewsByHomestayHomestayId(homestayId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUserUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserUserId(userId));
    }

    @GetMapping("/rate/{rate}")
    public ResponseEntity<List<Review>> getReviewsByRate(@PathVariable Integer rate) {
        return ResponseEntity.ok(reviewService.getReviewsByRate(rate));
    }

    @GetMapping("/AverageRateHomestay/{homestayId}")
    public double getHomestayWithAverageRate(@PathVariable Long homestayId){
        return reviewService.getHomestayWithAverageRate(homestayId);
    }

    @GetMapping("/getHomestayByHighRate")
    public List<Homestay> getHomestayByHighRate(){
        return reviewService.getHomestaysByHighRate();
    }

    @GetMapping("/search/keyword")
    public ResponseEntity<List<Review>> getReviewsByKeyword(@RequestParam("keyword") String keyword) {
        List<Review> result = reviewService.getReviewsByKeyword(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/percentGoodHomestay")
    public int percentGoodHomestay(){
        return reviewService.percentGoodHomestay();
    }

    @GetMapping("/percentNotGoodHomestay")
    public int percentNotGoodHomestay(){
        return reviewService.percentNotGoodHomestay();
    }
}