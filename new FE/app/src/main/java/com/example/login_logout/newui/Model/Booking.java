package com.example.login_logout.newui.Model;

import java.util.List;

public class Booking {
    private Long bookingId;
    private String checkInDate;
    private String checkOutDate;
    private double depositPrice;
    private double totalPrice;
// ...và sửa lại tất cả getter/setter tương ứng

    private int status;
    private String created_at;
    private String updated_at;
    private User user;
    private List<Room> rooms;
    public Booking() {}

    public Booking(Long booking_id, String checkInDate, String checkOutDate, double depositPrice, double totalPrice, int status, String created_at, String updated_at, User user, List<Room> listRoom) {
        this.bookingId = booking_id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.depositPrice = depositPrice;
        this.totalPrice = totalPrice;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user = user;
        this.rooms = listRoom;
    }


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(double depositPrice) {
        this.depositPrice = depositPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Room> getListRoom() {
        return rooms;
    }

    public void setListRoom(List<Room> listRoom) {
        this.rooms = listRoom;
    }

}
