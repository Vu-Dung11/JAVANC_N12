package com.example.backendTeam12.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.model.Review;
import com.example.backendTeam12.repository.HomestayRepository;
import com.example.backendTeam12.repository.ReviewRepository;
import com.example.backendTeam12.service.ReviewService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HomestayRepository homestayRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
    public double getHomestayWithAverageRate(Long homestayId) {
        Optional<Homestay> homestayOpt = homestayRepository.findById(homestayId);
        if (homestayOpt.isEmpty()) {
            return 0; // Trả về 0 nếu không tìm thấy Homestay
        }

        List<Review> reviews = reviewRepository.findByHomestayHomestayId(homestayId);

        if (reviews.isEmpty()) {
            return 0; // Trả về 0 nếu không có Review
        }

        double averageRate = reviews.stream()
                .filter(review -> review.getRate() != null) // Loại bỏ các rate null
                .mapToInt(Review::getRate)
                .average()
                .orElse(0.0);

        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(averageRate));
    }

    @Override
    public List<Homestay> getHomestaysByHighRate() {
        List<Homestay> homestays = reviewRepository.getGHomestaysByHighRate();
        if (homestays.isEmpty()){
            homestays = homestayRepository.findAll();
        }
        
        return homestays;
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

    @Override
    public List<Review> getReviewsByKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()){
            return reviewRepository.findAll();
        }
        String lowerKeyword = "%" + keyword.toLowerCase() + "%";
        String jpql = "SELECT h FROM Review h WHERE " +
                      "LOWER(h.comment) LIKE :kw";
        TypedQuery<Review> query = entityManager.createQuery(jpql, Review.class);
        query.setParameter("kw", lowerKeyword);
        return query.getResultList();
    }

    @Override
    public int percentGoodHomestay(){
        long totalReview = reviewRepository.getTotalReviewRate();
        if (totalReview == 0) return 0;

        long goodReview = reviewRepository.getTotalGoodReviewRate();
        return (int)(((double) goodReview / totalReview) * 100 );
    }
    
    @Override
    public int percentNotGoodHomestay(){
        long totalReview = reviewRepository.getTotalReviewRate();
        if (totalReview == 0) return 0;

        long notGoodReview = totalReview - reviewRepository.getTotalGoodReviewRate();
        return (int)(((double) notGoodReview / totalReview) * 100 );
    }
}
