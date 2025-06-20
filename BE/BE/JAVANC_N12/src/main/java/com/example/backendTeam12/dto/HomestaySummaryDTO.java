package com.example.backendTeam12.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomestaySummaryDTO {
    private Long homestayId;
    private String name;
    private String address;
    private Double averagePrice;
    private List<Integer> roomTypes;
    private String roomTypeDescription;
    private double rateHs;

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public List<Integer> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<Integer> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public String getRoomTypeDescription() {
        return roomTypeDescription;
    }

    public void setRoomTypeDescription(String roomTypeDescription) {
        this.roomTypeDescription = roomTypeDescription;
    }

    public HomestaySummaryDTO(Long homestayId, String name, String address, Double averagePrice, List<Integer> roomTypes, double rateHs) {
        this.homestayId = homestayId;
        this.name = name;
        this.address = address;
        this.averagePrice = averagePrice;
        this.roomTypes = roomTypes;
        this.roomTypeDescription = convertRoomTypesToString(roomTypes); 
        this.rateHs = rateHs;
    }
    
    public HomestaySummaryDTO(String name, String address, Double averagePrice, Double rateHs) {
        this.name = name;
        this.address = address;
        this.averagePrice = averagePrice;
        this.rateHs = rateHs != null ? rateHs : 0.0;
    }


    private String convertRoomTypesToString(List<Integer> types) {
        if (types == null || types.isEmpty()) return "Không xác định";

        Set<Integer> uniqueTypes = new HashSet<>(types);

        if (uniqueTypes.contains(4)) return "Đủ loại phòng";

        boolean has1 = uniqueTypes.contains(1);
        boolean has2 = uniqueTypes.contains(2);
        boolean has3 = uniqueTypes.contains(3);

        if (has1 && has2 && !has3) return "Phòng đơn + Phòng đôi";
        if (has1 && !has2 && !has3) return "Phòng đơn";
        if (!has1 && has2 && !has3) return "Phòng đôi";
        if (has3 && !has1 && !has2) return "Phòng ghép";
        if (has1 && has2 && has3) return "Đủ loại phòng";
        if ((has1 || has2) && has3) return "Phòng đơn/đôi + Phòng ghép";

        return "Không xác định";
    }

    public double getRateHs() {
        return rateHs;
    }

    public void setRateHs(double rateHs) {
        this.rateHs = rateHs;
    }

    public Long getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(Long homestayId) {
        this.homestayId = homestayId;
    }

}