package com.example.login_logout.data.model2;

import java.util.Date;

public class PhotoDTO {
    private Long photoId;
    private String namePhoto;
    private Date createdAt;
    private Date updatedAt;
    private Long homestayId;

    // Constructor
    public PhotoDTO() {}

    public PhotoDTO(Long photoId, String namePhoto, Date createdAt, Date updatedAt, Long homestayId) {
        this.photoId = photoId;
        this.namePhoto = namePhoto;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.homestayId = homestayId;
    }

    // Getters and Setters
    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getNamePhoto() {
        return namePhoto;
    }

    public void setNamePhoto(String namePhoto) {
        this.namePhoto = namePhoto;
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
}
