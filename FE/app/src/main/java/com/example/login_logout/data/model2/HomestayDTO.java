package com.example.login_logout.data.model2;

import java.time.LocalDateTime;

public class HomestayDTO {
    private Long homestayId;
    private String name;
    private String description;
    private String ward;
    private String district;
    private String province;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

    // Constructor
    public HomestayDTO() {}

    public HomestayDTO(Long homestayId, String name, String description, String ward, 
                   String district, String province, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
        this.homestayId = homestayId;
        this.name = name;
        this.description = description;
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(Long homestayId) {
        this.homestayId = homestayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
