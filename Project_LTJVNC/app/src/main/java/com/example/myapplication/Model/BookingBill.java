package com.example.myapplication.Model;

public class BookingBill {
    private Long bookingId;
    private Long roomId;
    private String roomName;
    private String checkInDate;
    private double totalPrice;
    private double depositPrice;

    public BookingBill(Long bookingId, Long roomId, String roomName, String checkInDate, double totalPrice, double depositPrice) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.roomName = roomName;
        this.checkInDate = checkInDate;
        this.totalPrice = totalPrice;
        this.depositPrice = depositPrice;
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

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(double depositPrice) {
        this.depositPrice = depositPrice;
    }
}
