package com.example.backendTeam12.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Homestays")
public class Homestay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "homestay_id")
    private Long homestayId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String ward;
    private String district;
    private String province;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "homestay", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "homestay", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "homestay", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @ManyToMany
    @JoinTable(
        name = "Services_advantage",
        joinColumns = @JoinColumn(name = "homestay_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}