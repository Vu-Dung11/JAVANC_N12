package com.example.backendTeam12.dto;

import java.time.LocalDateTime;

public class HistoryBookingDTO {
    private Long userId;
    private String roomName;
    private String homestayName;
    private String address;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;


    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getHomestayName() {
        return homestayName;
    }
    public void setHomestayName(String homestayName) {
        this.homestayName = homestayName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }
    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    
    public HistoryBookingDTO(Long userId, String roomName, String homestayName, String address,
            LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        this.userId = userId;
        this.roomName = roomName;
        this.homestayName = homestayName;
        this.address = address;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    
}



