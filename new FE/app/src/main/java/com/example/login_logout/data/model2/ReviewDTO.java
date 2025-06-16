package com.example.login_logout.data.model2;

import java.util.Date;

public class ReviewDTO {
    private Long reviewId;
    private Integer rate;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
    private Long homestayId;
    private Long userId;

    // Constructor
    public ReviewDTO() {}

    public ReviewDTO(Long reviewId, Integer rate, String comment, Date createdAt,
                 Date updatedAt, Long homestayId, Long userId) {
        this.reviewId = reviewId;
        this.rate = rate;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.homestayId = homestayId;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(Long homestayId) {
        this.homestayId = homestayId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
