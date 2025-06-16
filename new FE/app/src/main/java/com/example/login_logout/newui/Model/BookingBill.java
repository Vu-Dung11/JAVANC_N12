package com.example.login_logout.newui.Model;

import java.util.Date;

public class BookingBill {
    private Long bookingId;
    private Long roomId;
    private String roomName;
    private String address;
    private Date checkInDate;
    private Double totalPrice;
    private Double depositPrice;
    private Date createdAt;

    public BookingBill(Long bookingId, Long roomId, String roomName, String address, Date checkInDate,
                       Double totalPrice, Double depositPrice, Date createdAt) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.roomName = roomName;
        this.address = address;
        this.checkInDate = checkInDate;
        this.totalPrice = totalPrice;
        this.depositPrice = depositPrice;
        this.createdAt = createdAt;
    }

    // Getter - Setter

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(Double depositPrice) {
        this.depositPrice = depositPrice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}