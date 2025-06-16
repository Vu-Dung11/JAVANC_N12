package com.example.login_logout.data.model2;

import java.util.List;

public class SearchHomestayDTO {
    private Long homestayId;
    private String name;
    private String address;
    private Double averagePrice;
    private List<Integer> roomTypes;
    private String roomTypeDescription;
    private Double rateHs;
    private int imageResourceId; // Thêm trường này

    public SearchHomestayDTO() {}

    public SearchHomestayDTO(Long homestayId, String name, String address, Double averagePrice,
                             List<Integer> roomTypes, String roomTypeDescription, Double rateHs, int imageResourceId) {
        this.homestayId = homestayId;
        this.name = name;
        this.address = address;
        this.averagePrice = averagePrice;
        this.roomTypes = roomTypes;
        this.roomTypeDescription = roomTypeDescription;
        this.rateHs = rateHs;
        this.imageResourceId = imageResourceId;
    }

    // Getters and Setters
    public Long getHomestayId() { return homestayId; }
    public void setHomestayId(Long homestayId) { this.homestayId = homestayId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getAveragePrice() { return averagePrice; }
    public void setAveragePrice(Double averagePrice) { this.averagePrice = averagePrice; }
    public List<Integer> getRoomTypes() { return roomTypes; }
    public void setRoomTypes(List<Integer> roomTypes) { this.roomTypes = roomTypes; }
    public String getRoomTypeDescription() { return roomTypeDescription; }
    public void setRoomTypeDescription(String roomTypeDescription) { this.roomTypeDescription = roomTypeDescription; }
    public Double getRateHs() { return rateHs; }
    public void setRateHs(Double rateHs) { this.rateHs = rateHs; }
    public int getImageResourceId() { return imageResourceId; }
    public void setImageResourceId(int imageResourceId) { this.imageResourceId = imageResourceId; }
}