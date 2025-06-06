package com.example.backendTeam12.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backendTeam12.model.Room;

public interface  RoomRepository extends JpaRepository<Room, Long>{
    public List<Room> findByBookingBookingId(Long bookingId);
    public List<Room> findByHomestayHomestayId(Long homestayId);
}
