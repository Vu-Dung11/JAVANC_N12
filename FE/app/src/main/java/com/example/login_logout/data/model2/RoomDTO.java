package com.example.login_logout.data.model2;

import java.util.Date;

public class RoomDTO {
    private Long roomId;
    private String roomName;
    private Integer roomType;
    private Double price;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;
    private Long homestayId;
    private Long bookingId;

    // Constructor
    public RoomDTO() {}

    public RoomDTO(Long roomId, String roomName, Integer roomType, Double price, 
               Integer status, Date createdAt, Date updatedAt, Long homestayId, Long bookingId) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.homestayId = homestayId;
        this.bookingId = bookingId;
    }

    // Getters and Setters
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
