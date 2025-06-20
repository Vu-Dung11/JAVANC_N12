package com.example.backendTeam12.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "booking_history")
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "check_in_date")
    private LocalDateTime checkInDate;

    @Column(name = "total_price", precision = 38, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "deposit_price", precision = 38, scale = 2)
    private BigDecimal depositPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
