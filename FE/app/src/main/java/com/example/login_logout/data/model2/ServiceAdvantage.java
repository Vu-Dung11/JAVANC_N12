package com.example.login_logout.data.model2;

public class ServiceAdvantage {
    private Long homestayId;
    private Long serviceId;
    private String description;

    // Constructor
    public ServiceAdvantage() {}

    public ServiceAdvantage(Long homestayId, Long serviceId, String description) {
        this.homestayId = homestayId;
        this.serviceId = serviceId;
        this.description = description;
    }

    // Getters and Setters
    public Long getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(Long homestayId) {
        this.homestayId = homestayId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
} 