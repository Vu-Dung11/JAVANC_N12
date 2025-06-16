package com.example.login_logout.data.model2;

import java.time.LocalDateTime;

public class HomestaySummaryDTO {
    private Long homestayId;
    private String name;
    private String description;
    private String address;
    private String imageUrl;
    private Double rating;
    private Integer reviewCount;
    private Double price;
    private String roomTypeDescription;
    private Double averagePrice;
    private Double rateHs;
    private int imageResourceId; // Thêm trường này

    public HomestaySummaryDTO() {}

    public HomestaySummaryDTO(Long homestayId, String name, String description, String address,
                              String imageUrl, Double rating, Integer reviewCount, Double price,
                              String roomTypeDescription, Double averagePrice, Double rateHs, int imageResourceId) {
        this.homestayId = homestayId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.price = price;
        this.roomTypeDescription = roomTypeDescription;
        this.averagePrice = averagePrice;
        this.rateHs = rateHs;
        this.imageResourceId = imageResourceId;
    }

    // Getters and Setters
    public Long getHomestayId() { return homestayId; }
    public void setHomestayId(Long homestayId) { this.homestayId = homestayId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Integer getReviewCount() { return reviewCount; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getRoomTypeDescription() { return roomTypeDescription; }
    public void setRoomTypeDescription(String roomTypeDescription) { this.roomTypeDescription = roomTypeDescription; }
    public Double getAveragePrice() { return averagePrice; }
    public void setAveragePrice(Double averagePrice) { this.averagePrice = averagePrice; }
    public Double getRateHs() { return rateHs; }
    public void setRateHs(Double rateHs) { this.rateHs = rateHs; }
    public int getImageResourceId() { return imageResourceId; }
    public void setImageResourceId(int imageResourceId) { this.imageResourceId = imageResourceId; }
}